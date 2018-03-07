package nexuslink.charon.smartdoor.contract.main;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2018/3/5 17:18
 * 修改人：Charon
 * 修改时间：2018/3/5 17:18
 * 修改备注：
 */

public interface MainContract {
    interface Model {
    }

    interface View {
        void translationDoor(int startX,int startY,int width,int height,int resource);

        void translationTip();

        void changeMode(int id);
    }

    interface Presenter {
    }
}
