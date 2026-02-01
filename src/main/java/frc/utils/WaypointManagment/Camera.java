package frc.utils.WaypointManagment;

import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import frc.team1699.subsystems.VisionSubsystem;

public class Camera {
    private PhotonCamera cam;
    private List<PhotonPipelineResult> results;
    private PhotonTrackedTarget currentTag;
    private double xOffset, yOffset, yawOffset;
    /**Camera class to hold positional and camera data. 
     * automatically sets pipeline to 1 upon construction.
     * @param name Name of Camera
     * @param offsets {x,y,yaw}, holds positional data of camera
     */
    public Camera(String name, double[] offsets) {
        this.cam = new PhotonCamera(name);
        this.xOffset = offsets[0];
        this.yOffset = offsets[1];
        this.yawOffset = offsets[2];

        this.setPipelineIndex(1);
    }

    public void setPipelineIndex(int index) {
        this.cam.setPipelineIndex(index);
    }

    public PhotonCamera getCam() {
        return this.cam;
    }

    private void setResults() {
        this.results= this.cam.getAllUnreadResults();
    }

    public PhotonPipelineResult getLatestResult() {
        return this.results.get(results.size()-1);
    }

    public boolean hasValidTags() {
        return !results.isEmpty() && getLatestResult().hasTargets();
    }

    public void setLowestAmbiguity() {
        setResults();
        if(hasValidTags()) {
            double lowestAmbuguity=1;
            PhotonTrackedTarget bestTag=null;
            for (var tag : getLatestResult().getTargets()) {
                if(tag==null) {
                    continue;
                }
                if(
                    tag.getPoseAmbiguity()<lowestAmbuguity 
                        && VisionSubsystem.currentWaypoint.waypoint.hasId(tag.fiducialId) 
                        && tag.getPoseAmbiguity() != -1
                ) {
                    lowestAmbuguity = tag.getPoseAmbiguity();
                    bestTag=tag;
                }
            } 
            this.currentTag = bestTag;
            return;
        }
        this.currentTag = null;
        return;
    }

    public PhotonTrackedTarget getCurrentTag() {
        return this.currentTag;
    }

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getYawOffset() {
        return this.yawOffset;
    }
}
