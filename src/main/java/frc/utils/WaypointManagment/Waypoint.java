package frc.utils.WaypointManagment;

import java.util.HashMap;

/** Describes a target for bot angular or positional management
 *  for vision. For example, the Hub is a waypoint.
 */
public class Waypoint {
    private HashMap<Integer,double[]> targetOffsetMap = new HashMap<>();

    /** Uses targetOffsetMap to relate any ID to an offset (i.e.: waypoint)
     * @param tags april tags of waypoint
     */
    public Waypoint(AprilTagPoint ...tags) {
        for(AprilTagPoint tag : tags) {
            this.targetOffsetMap.put(tag.getID(), tag.getOffset());
        }
    }
    /** returns the {x,y} offset from a tag to a waypoint.
     *  NOT INTENDED FOR A REPLACEMENT OF hasID.
     *  @param id Id of the april tag
     */
    public double[] getOffset(int id) {
        return this.targetOffsetMap.get(id);
    }

    /** returns if array has specified id.
     * @param id Id of a tag
     **/
    public boolean hasId(int id) {
        return this.targetOffsetMap.containsKey(id);
    }
}
