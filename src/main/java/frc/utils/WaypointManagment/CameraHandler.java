package frc.utils.WaypointManagment;

import org.photonvision.targeting.PhotonTrackedTarget;

import frc.robot.Constants.VisionConstants;
import frc.team1699.subsystems.VisionSubsystem;

public class CameraHandler {
    private Camera[] cams;
    private Camera targetCam;

    private double xOffset,yOffset;
    public CameraHandler(Camera ...cams) {
        this.cams=cams;
    }

    public PhotonTrackedTarget getBestTag() {
        PhotonTrackedTarget bestTag=null;
        for(var cam: cams) {
            cam.setLowestAmbiguity();
            var currentTag=cam.getCurrentTag();
            if(cam.getCurrentTag()==null) {
                continue;
            }
            if(
                currentTag.getPoseAmbiguity()<VisionSubsystem.currentAmbiguity*VisionConstants.ambiguityTolerance
                    && currentTag.getPoseAmbiguity() != -1
                    && hasTargetTag(currentTag.fiducialId)
            ) {
                VisionSubsystem.currentAmbiguity = currentTag.getPoseAmbiguity();
                bestTag=currentTag;
                targetCam=cam;
            }
        }
        setOffsets();
        return bestTag;
    }  

    private boolean hasTargetTag(int id) {
        return VisionSubsystem.currentWaypoint.waypoint.hasId(id);
    }
    
    private void setOffsets() {
        if(targetCam!= null) {
            this.xOffset=targetCam.getXOffset();
            this.yOffset=targetCam.getYOffset();
        }
    }

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }
}
