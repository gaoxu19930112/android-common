package com.android.common.core.updateapp;

import java.io.Serializable;
import java.util.List;

/**
 * <pre>
 *     author : gaoxu
 *     e-mail : 511527070@qq.com
 *     time   : 2018/10/12
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class RspAppVersion implements Serializable {

    /**
     * endRow : 0
     * firstPage : 0
     * hasNextPage : true
     * hasPreviousPage : true
     * isFirstPage : true
     * isLastPage : true
     * lastPage : 0
     * list : [{"desci":"string","forceUpdate":0,"minVersion":"string","saveFile":"string","savePath":"string","softCode":"string","softContent":"string","softCurversion":"string","softFlag":0,"softName":"string","softPubversion":"string","softType":"string"}]
     * navigateFirstPage : 0
     * navigateLastPage : 0
     * navigatePages : 0
     * navigatepageNums : [0]
     * nextPage : 0
     * pageNum : 0
     * pageSize : 0
     * pages : 0
     * prePage : 0
     * size : 0
     * startRow : 0
     * total : 0
     */

    private int endRow;
    private int firstPage;
    private boolean hasNextPage;
    private boolean hasPreviousPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private int lastPage;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int navigatePages;
    private int nextPage;
    private int pageNum;
    private int pageSize;
    private int pages;
    private int prePage;
    private int size;
    private int startRow;
    private int total;
    private List<AppVersionBean> list;
    private List<Integer> navigatepageNums;

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<AppVersionBean> getList() {
        return list;
    }

    public void setList(List<AppVersionBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class AppVersionBean implements Serializable {
        /**
         * desci : string
         * forceUpdate : 0
         * minVersion : string
         * saveFile : string
         * savePath : string
         * softCode : string
         * softContent : string
         * softCurversion : string
         * softFlag : 0
         * softName : string
         * softPubversion : string
         * softType : string
         */

        private String desci;
        private int forceUpdate;
        private String minVersion;
        private String saveFile;
        private String savePath;
        private String softCode;
        private String softContent;
        private String softCurversion;
        private int softFlag;
        private String softName;
        private String softPubversion;
        private String softType;

        public AppVersionBean(int forceUpdate, String minVersion, String softPubversion, String softContent) {
            this.forceUpdate = forceUpdate;
            this.minVersion = minVersion;
            this.softPubversion = softPubversion;
            this.softContent = softContent;
        }

        public String getDesci() {
            return desci;
        }

        public void setDesci(String desci) {
            this.desci = desci;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public String getMinVersion() {
            return minVersion;
        }

        public void setMinVersion(String minVersion) {
            this.minVersion = minVersion;
        }

        public String getSaveFile() {
            return saveFile;
        }

        public void setSaveFile(String saveFile) {
            this.saveFile = saveFile;
        }

        public String getSavePath() {
            return savePath;
        }

        public void setSavePath(String savePath) {
            this.savePath = savePath;
        }

        public String getSoftCode() {
            return softCode;
        }

        public void setSoftCode(String softCode) {
            this.softCode = softCode;
        }

        public String getSoftContent() {
            return softContent;
        }

        public void setSoftContent(String softContent) {
            this.softContent = softContent;
        }

        public String getSoftCurversion() {
            return softCurversion;
        }

        public void setSoftCurversion(String softCurversion) {
            this.softCurversion = softCurversion;
        }

        public int getSoftFlag() {
            return softFlag;
        }

        public void setSoftFlag(int softFlag) {
            this.softFlag = softFlag;
        }

        public String getSoftName() {
            return softName;
        }

        public void setSoftName(String softName) {
            this.softName = softName;
        }

        public String getSoftPubversion() {
            return softPubversion;
        }

        public void setSoftPubversion(String softPubversion) {
            this.softPubversion = softPubversion;
        }

        public String getSoftType() {
            return softType;
        }

        public void setSoftType(String softType) {
            this.softType = softType;
        }
    }
}
