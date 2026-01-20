package frc.utils.WaypointManagment;

import org.photonvision.PhotonCamera;

public class Camera {
    private PhotonCamera cam;
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

    public double getXOffset() {
        return this.xOffset;
    }

    public double getYOffset() {
        return this.yOffset;
    }

    public double getYaw() {
        return this.yawOffset;
    }
}
