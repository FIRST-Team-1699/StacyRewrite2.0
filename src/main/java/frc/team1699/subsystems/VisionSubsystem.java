package frc.team1699.subsystems;

import java.util.List;

import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.net.PortForwarder;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.PhotonvisionConstants;
import frc.utils.WaypointManagment.*;

public class VisionSubsystem extends SubsystemBase {
    private TagWaypoint currentWaypoint;
    private int targetTagId;

    // private PhotonCamera cam1;// cam2;
    private Camera cam1; // cam2
    private boolean hasTag;
    private double yaw, x, y, z, xWaypointOffset, yWaypointOffset, xCamOffset, yCamOffset, yawCamOffset, distanceToTag;

    public VisionSubsystem () {
        cam1 = new Camera(
            PhotonvisionConstants.kCamOneName,
            new double[]{
                PhotonvisionConstants.cam1XOffset,
                PhotonvisionConstants.cam1YOffset,
                PhotonvisionConstants.cam1YawOffset
            }
        );
        // cam2 = new Camera(
        //     PhotonvisionConstants.kCamOneName,
        //     new double[]{
        //         PhotonvisionConstants.cam2XOffset,
        //         PhotonvisionConstants.cam2YOffset,
        //         PhotonvisionConstants.cam2YawOffset
        //     }
        // );

        PortForwarder.add(5800, "photonvision.local:5800", 5800);
        currentWaypoint=TagWaypoint.NONE;
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
        double tempDegrees = (Math.atan(this.x+xWaypointOffset/this.y+yWaypointOffset))*180/Math.PI;
        tempDegrees += this.yawCamOffset;
        this.yaw= (tempDegrees <0.0 ? tempDegrees+90 : tempDegrees-90 );
    }

    public void setDistanceToTag() {
        double tempDistance = Math.pow(this.x, 2.0) + Math.pow(this.y, 2.0);
        this.distanceToTag = Math.sqrt(tempDistance);
    }

    public Command setWaypoint(TagWaypoint waypoint) {
        return runOnce(() -> {
            this.currentWaypoint = waypoint;
        });
    }

    @Override
    public void periodic() {
        if (currentWaypoint==TagWaypoint.NONE) {
            this.hasTag=false;
            return;
        }

        var camOneTag = getCamTag(this.cam1.getCam().getAllUnreadResults());
        // var camTwoTag = getCamTag(this.cam2.getAllUnreadResults());
        // var bestTag = bestOfTags(camOneTag,camTwoTag);
        PhotonTrackedTarget bestTag = bestOfTags(camOneTag);
        if (bestTag!=null) {
            setOffsets(bestTag, camOneTag);

            this.targetTagId=bestTag.getFiducialId();
            this.xWaypointOffset=currentWaypoint.waypoint.getOffset(this.targetTagId)[0];
            this.yWaypointOffset=currentWaypoint.waypoint.getOffset(this.targetTagId)[1];

            this.hasTag=true;
            this.x=bestTag.getBestCameraToTarget().getX()+xCamOffset;
            this.y=bestTag.getBestCameraToTarget().getY()+yCamOffset;
            this.z=bestTag.getBestCameraToTarget().getZ();

            setYawOnWaypoint();
            setDistanceToTag();
            System.out.println("PoseDist:" + this.getDistanceToTag());
            System.out.println("Confidence:" + bestTag.getDetectedObjectConfidence());
            return;
        }

        this.hasTag=false;
    }

    public PhotonTrackedTarget getCamTag(List<PhotonPipelineResult> camResults) {
        if(camResults==null) {
            return null;
        }
        if(!camResults.isEmpty()) {
            var result = camResults.get(camResults.size() - 1);
            if (result.hasTargets()) {
                return resolveTags(result);
            }
        }
        return null;
    }

    public PhotonTrackedTarget resolveTags(PhotonPipelineResult result) {
        double lowestAmbuguity=0.3;
        PhotonTrackedTarget bestTag=null;
        for (var tag : result.getTargets()) {
            if(tag==null) {
                continue;
            }
            if(tag.getPoseAmbiguity()<lowestAmbuguity && this.currentWaypoint.waypoint.hasId(tag.fiducialId)) {
                lowestAmbuguity = tag.getPoseAmbiguity();
                bestTag=tag;
            }
        } 
        return bestTag;
    }

    public boolean hasTargetTag(int id) {
        return this.currentWaypoint.waypoint.hasId(id);
    }

    public PhotonTrackedTarget bestOfTags(PhotonTrackedTarget ...tags) {
        double lowestAmbuguity=0.3;
        PhotonTrackedTarget bestTag=null;
        for(var tag: tags) {
            if(tag==null) {
                continue;
            }
            if(tag.getPoseAmbiguity()<lowestAmbuguity) {
                lowestAmbuguity = tag.getPoseAmbiguity();
                bestTag=tag;
            }
        }
        return bestTag;
    }

    private void setOffsets(PhotonTrackedTarget bestTag, PhotonTrackedTarget ...tags) {
        for(PhotonTrackedTarget tag: tags) {
            this.xCamOffset = tag == bestTag ? 
                cam1.getXOffset() : this.xCamOffset;
            this.yCamOffset = tag == bestTag ? 
                cam1.getYOffset() : this.yCamOffset;
            this.yawCamOffset = tag == bestTag ? 
                cam1.getYaw() : this.yawCamOffset;
        }
    }

    public enum TagWaypoint {
        NONE(),
        BASKET_PRACTICE(new Waypoint(            
            new AprilTagPoint(3, new double[]{1.41,0})
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
            new AprilTagPoint(5, new double[]{0,0}),
            new AprilTagPoint(8, new double[]{0,0}),
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
