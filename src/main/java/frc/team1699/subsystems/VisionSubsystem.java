package frc.team1699.subsystems;

import java.util.function.BooleanSupplier;

import org.photonvision.PhotonCamera;

import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PhotonvisionConstants;
import frc.robot.Constants.ShooterConstants;

public class VisionSubsystem extends SubsystemBase {
    private PhotonCamera cam1;
    // private PhotonCamera cam2;
    private boolean hasTag;
    private double yaw, x, y, z, distanceToTag;

    public VisionSubsystem () {
        cam1 = new PhotonCamera(PhotonvisionConstants.kCamOneName);

        cam1.setPipelineIndex(1);
        PortForwarder.add(5800, "photonvision.local:5800", 5800);
    }

    public double getYaw() {
        return this.yaw;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public boolean getHasTag() {
        return this.hasTag;
    }

    public double getDistanceToTag() {
        return this.distanceToTag;
    }

    public void setYawOnPose() {
        double tempDegrees = (Math.atan(this.x/this.y))*180/Math.PI;
        this.yaw= (tempDegrees <0.0 ? tempDegrees+90 : tempDegrees-90 );
    }

    public void setDistanceToTag() {
        double tempDistance = Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0);
        this.distanceToTag = Math.sqrt(tempDistance);
    }

    public double getBasketYaw() {
        double tempDegrees = (Math.atan((this.x+1.41)/this.y))*180/Math.PI;
        return (tempDegrees <0.0 ? tempDegrees+90 : tempDegrees-90 );
    }

    @Override
    public void periodic() {
        var results = this.cam1.getAllUnreadResults();
        if (!results.isEmpty()) {
            try {
                var result = results.get(results.size() - 1);
                if (result.hasTargets()) {
                    for (var target : result.getTargets()) {
                        this.hasTag=true;
                        this.x=target.getBestCameraToTarget().getX()+PhotonvisionConstants.cam1XOffset;
                        this.y=target.getBestCameraToTarget().getY()+PhotonvisionConstants.cam1YOffset;
                        this.z=target.getBestCameraToTarget().getZ();
                        setYawOnPose();
                        setDistanceToTag();
                    } 
                }
            } catch (Exception e) {
                this.hasTag=false;
            }
        }
        System.out.println("PoseDist:" + this.getDistanceToTag());
    }
}
