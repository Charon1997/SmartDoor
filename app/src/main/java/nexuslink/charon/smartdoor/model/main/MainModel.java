package nexuslink.charon.smartdoor.model.main;

import nexuslink.charon.smartdoor.contract.main.MainContract;

/**
 * 项目名称：SmartDoor
 * 类描述：
 * 创建人：Charon
 * 创建时间：2018/3/5 17:18
 * 修改人：Charon
 * 修改时间：2018/3/5 17:18
 * 修改备注：
 */

public class MainModel implements MainContract.Model {
    private String tag;
    private int size;
    private SceneModel sceneModel;
    private ItemModel itemModel;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }



    public class ItemModel {
        private int id;
        private String name;
        private int img;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public ItemModel(int id, String name, int img) {
            this.id = id;
            this.name = name;
            this.img = img;
        }


    }
}
