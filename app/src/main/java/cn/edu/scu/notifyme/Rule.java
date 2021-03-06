package cn.edu.scu.notifyme;

import androidx.annotation.Nullable;

/**
 * Rule
 * 规则对象
 */
public class Rule {
    /**
     * ID，数据库主键
     */
    private long id;
    /**
     * 规则名
     */
    private String name;
    /**
     * 是否启用
     */
    private boolean isActive;
    /**
     * 运行间隔
     */
    private int duration;
    /**
     * 图标URL
     */
    private String iconUrl;
    /**
     * 要访问的URL
     */
    private String toLoadUrl;
    /**
     * 运行的脚本字符串
     */
    private String script;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getToLoadUrl() {
        return toLoadUrl;
    }

    public void setToLoadUrl(String toLoadUrl) {
        this.toLoadUrl = toLoadUrl;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode() ^ this.script.hashCode();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Rule)) return false;
        Rule thatRule = (Rule) obj;
        if (!this.getName().equals(thatRule.getName())) return false;
        if (this.getDuration() != thatRule.getDuration()) return false;
        if (!this.getToLoadUrl().equals(thatRule.getToLoadUrl())) return false;
        if (this.isActive() != thatRule.isActive()) return false;
        if (!this.getScript().equals(thatRule.getScript())) return false;
        return true;
    }
}
