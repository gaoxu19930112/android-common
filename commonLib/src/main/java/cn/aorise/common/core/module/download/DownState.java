package cn.aorise.common.core.module.download;


/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   : 下载状态
 *     version: 1.0
 * </pre>
 */
public enum  DownState {
    START(0),
    READY(1),
    DOWN(2),
    PAUSE(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownState(int state) {
        this.state = state;
    }
}
