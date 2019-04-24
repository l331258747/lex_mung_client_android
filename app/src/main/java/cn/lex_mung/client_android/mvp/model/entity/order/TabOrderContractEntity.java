package cn.lex_mung.client_android.mvp.model.entity.order;

import java.util.ArrayList;
import java.util.List;

public class TabOrderContractEntity {

    String imgHead;
    String name;
    String fileName;
    String fileType;
    String fileSize;
    String time;
    String read;

    public String getImgHead() {
        return imgHead;
    }

    public void setImgHead(String imgHead) {
        this.imgHead = imgHead;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRead() {
        return read;
    }

    public void setRead(String read) {
        this.read = read;
    }

    public List<TabOrderContractEntity> getDatas(){
        List<TabOrderContractEntity> datas = new ArrayList<>();
        TabOrderContractEntity data = new TabOrderContractEntity();
        data.setImgHead("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556081620852&di=88a49fc4fadbeed767dc7ed037fe581e&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2Fcc11728b4710b91250f4e5dbc9fdfc03924522d9.jpg");
        data.setName("雷雷露");
        data.setFileName("这里是用户或者律师发送的&#xA;文档名称.doc");
        data.setFileType("doc");
        data.setFileSize("22kb");
        data.setTime("2019-01-01");
        data.setRead("律师已读");
        datas.add(data);
        datas.add(data);
        datas.add(data);
        datas.add(data);
        return datas;
    }
}
