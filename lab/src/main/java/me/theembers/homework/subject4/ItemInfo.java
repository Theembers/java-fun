package me.theembers.homework.subject4;

/**
 * @author TheEmbers Guo
 * @version 1.0
 * createTime 2019-12-16 8:30 下午
 */
public class ItemInfo {
    /**
     * id
     */
    private String id;
    /**
     * 分组
     */
    private String groupId;
    /**
     * 指标
     */
    private Float quota;


    public ItemInfo() {
    }

    public ItemInfo(String id, String groupId, Float quota) {
        this.id = id;
        this.groupId = groupId;
        this.quota = quota;
    }

    @Override
    public String toString() {
        return "groupId: " + groupId + " id: " + id + " quota: " + quota;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public Float getQuota() {
        return quota;
    }

    public void setQuota(Float quota) {
        this.quota = quota;
    }
}
