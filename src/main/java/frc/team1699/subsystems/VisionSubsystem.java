package frc.team1699.subsystems;

import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConstants;
import frc.utils.WaypointManagment.*;

public class VisionSubsystem extends SubsystemBase {
    public static TagWaypoint currentWaypoint;
    public static double currentAmbiguity;
    private int targetTagId;

    private Camera cam2, cam1;
    private CameraHandler camHandler;
    private boolean hasTag;
    private double yaw, x, y, z, xWaypointOffset, yWaypointOffset, distanceToTag; // yawCameraOffset;

    public VisionSubsystem () {
        cam1 = new Camera(
            VisionConstants.kCamOneName,
            new double[]{
                VisionConstants.cam1XOffset,
                VisionConstants.cam1YOffset,
                VisionConstants.cam1YawOffset
            }
        );
        cam2 = new Camera(
            VisionConstants.kCamTwoName,
            new double[]{
                VisionConstants.cam2XOffset,
                VisionConstants.cam2YOffset,
                VisionConstants.cam2YawOffset
            }
        );
        // camHandler= new CameraHandler(cam1,cam2);
        camHandler= new CameraHandler(cam2);

        PortForwarder.add(5800, "photonvision.local:5800", 5800);
        currentWaypoint=TagWaypoint.CAMERA_TUNE;
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

    public void setYawOnWaypoint() {
        double tempDegrees = (Math.atan((this.x+xWaypointOffset)/(this.y+yWaypointOffset))*180/Math.PI);
        this.yaw= (tempDegrees <0.0 ? tempDegrees + 90 : tempDegrees -90 );
    }

    public void setDistanceToTag() {
        double tempDistance = Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0);
        this.distanceToTag = Math.sqrt(tempDistance);
    }

    public void setWaypoint(TagWaypoint waypoint) {
        currentWaypoint = waypoint;
    }

    PhotonTrackedTarget bestTag;
    @Override
    public void periodic() {
        if (currentWaypoint==TagWaypoint.NONE) {
            return;
        }
        bestTag=camHandler.getBestTag();
        if (bestTag!=null) {
            this.targetTagId=bestTag.getFiducialId();
            this.xWaypointOffset=currentWaypoint.waypoint.getOffset(this.targetTagId)[0];
            this.yWaypointOffset=currentWaypoint.waypoint.getOffset(this.targetTagId)[1];

            this.hasTag=true;
            this.x=bestTag.getBestCameraToTarget().getX() + camHandler.getXOffset();
            this.y=bestTag.getBestCameraToTarget().getY() + camHandler.getYOffset();
            this.z=bestTag.getBestCameraToTarget().getZ();

            setYawOnWaypoint();
            setDistanceToTag();
            // System.out.println("X, Y:" + this.x + "," + this.y);
            // System.out.println("Yaw:" + this.getYaw());
            // System.out.println("Actual Yaw:" + bestTag.getYaw());
            // System.out.println("Distance to score:" + this.distanceToTag);
            return;
        } 
        currentAmbiguity=1;
        this.hasTag=false;
    }


    public enum TagWaypoint {
        NONE(),
        CAMERA_TUNE(new Waypoint(            
            new AprilTagPoint(3, new double[]{0,0})
        )),
        BASKET_PRACTICE(new Waypoint(            
            new AprilTagPoint(2, new double[]{1.41,0})
        )),
        BLUE_HUB(new Waypoint(
            new AprilTagPoint(18, new double[]{0,0}),
            new AprilTagPoint(27, new double[]{0,0}),
            new AprilTagPoint(26, new double[]{0,0}),
            new AprilTagPoint(25, new double[]{0,0}),
            new AprilTagPoint(24, new double[]{0,0}),
            new AprilTagPoint(21, new double[]{0,0})
        )),
        BLUE_SHUFFLE_TOP(new Waypoint(
            new AprilTagPoint(17, new double[]{0,0}),
            new AprilTagPoint(19, new double[]{0,0})
        )),
        BLUE_SHUFFLE_BOTTOM(new Waypoint(
            new AprilTagPoint(20, new double[]{0,0}),
            new AprilTagPoint(22, new double[]{0,0})
        )),
        BLUE_HP(new Waypoint(
            new AprilTagPoint(29, new double[]{0,0})
        )),
        BLUE_GROUND_INTAKE(new Waypoint(
            new AprilTagPoint(23, new double[]{0,0})
        )),
        BLUE_CLIMB(new Waypoint(
            new AprilTagPoint(31, new double[]{0,0})
        )),

        RED_HUB(new Waypoint(
            // new AprilTagPoint(5, new double[]{0.46,-0.183}),
            new AprilTagPoint(8, new double[]{0.46,.342}),
            new AprilTagPoint(9, new double[]{0,0}),
            new AprilTagPoint(10, new double[]{0,0}),
            new AprilTagPoint(11, new double[]{0,0}),
            new AprilTagPoint(2, new double[]{0,0})
        )),
        RED_SHUFFLE_TOP(new Waypoint(
            new AprilTagPoint(6, new double[]{0,0}),
            new AprilTagPoint(4, new double[]{0,0})
        )),
        RED_SHUFFLE_BOTTOM(new Waypoint(
            new AprilTagPoint(3, new double[]{0,0}),
            new AprilTagPoint(1, new double[]{0,0})
        )),
        RED_HP(new Waypoint(
            new AprilTagPoint(13, new double[]{0,0})
        )),
        RED_GROUND_INTAKE(new Waypoint(
            new AprilTagPoint(7, new double[]{0,0})
        )),
        RED_CLIMB(new Waypoint(
            new AprilTagPoint(15, new double[]{0,0})
        )),
        
        NEUTRAL_TOP(new Waypoint(
            new AprilTagPoint(6, new double[]{0,0}),
            new AprilTagPoint(17, new double[]{0,0})
        )),
        NEUTRAL_BOTTOM(new Waypoint(
            new AprilTagPoint(1, new double[]{0,0}),
            new AprilTagPoint(22, new double[]{0,0})
        ));
        public Waypoint waypoint;
        TagWaypoint(Waypoint waypoint) {
            this.waypoint = waypoint;
        }
        TagWaypoint() {
            this.waypoint = new Waypoint(new AprilTagPoint(0,new double[]{0,0}));
        }
    }
}
