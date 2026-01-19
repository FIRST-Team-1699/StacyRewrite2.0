package frc.utils.WaypointManagment;

/**Handles the relationship between:
 * 1. Targeted ID Tags
 * 2. Their offset to a given waypoint
*/
public class AprilTagPoint {
    /**{x,y}, stores offset from tag to waypoint*/
    private double[] offsetToWaypoint;
    /**id of targeted tag*/
    private int id;

    /**@param id April Tag ID
     * @param offsetToWaypoint {x,y} offset to the waypoint
     */
    public AprilTagPoint(int id, double[] offsetToWaypoint) {
        this.id=id;
        this.offsetToWaypoint=offsetToWaypoint;
    }

    public int getID() {
        return this.id;
    }
    /** returns {x,y} */
    public double[] getOffset() {
        return this.offsetToWaypoint;
    }
}
