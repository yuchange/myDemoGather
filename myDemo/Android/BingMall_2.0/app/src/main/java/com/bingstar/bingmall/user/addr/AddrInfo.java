package com.bingstar.bingmall.user.addr;

import java.util.List;

/**
 * Created by liumengqiang on 2017/2/27.
 */

/**
 * 配送城市区域查询
 */
public class AddrInfo {

    private List<ProcListBean> procList;

    public List<ProcListBean> getProcList() {
        return procList;
    }

    public void setProcList(List<ProcListBean> procList) {
        this.procList = procList;
    }

    public static class ProcListBean {
        /**
         * cityList : [{"name":"北京市","areaList":[{"name":"东城区","areaId":"1"},{"name":"西城区","areaId":"2"},{"name":"崇文区","areaId":"3"},{"name":"宣武区","areaId":"4"},{"name":"朝阳区","areaId":"5"},{"name":"丰台区","areaId":"6"},{"name":"石景山区","areaId":"7"},{"name":"海淀区","areaId":"8"},{"name":"门头沟区","areaId":"9"},{"name":"房山区","areaId":"10"},{"name":"通州区","areaId":"11"},{"name":"顺义区","areaId":"12"},{"name":"昌平区","areaId":"13"},{"name":"大兴区","areaId":"14"},{"name":"怀柔区","areaId":"15"},{"name":"平谷区","areaId":"16"},{"name":"密云县","areaId":"17"},{"name":"延庆县","areaId":"18"}],"areaId":"1"}]
         * name : 北京
         * areaId : 1
         */

        private String name;
        private String areaId;
        private List<CityListBean> cityList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAreaId() {
            return areaId;
        }

        public void setAreaId(String areaId) {
            this.areaId = areaId;
        }

        public List<CityListBean> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean> cityList) {
            this.cityList = cityList;
        }

        public static class CityListBean {
            /**
             * name : 北京市
             * areaList : [{"name":"东城区","areaId":"1"},{"name":"西城区","areaId":"2"},{"name":"崇文区","areaId":"3"},{"name":"宣武区","areaId":"4"},{"name":"朝阳区","areaId":"5"},{"name":"丰台区","areaId":"6"},{"name":"石景山区","areaId":"7"},{"name":"海淀区","areaId":"8"},{"name":"门头沟区","areaId":"9"},{"name":"房山区","areaId":"10"},{"name":"通州区","areaId":"11"},{"name":"顺义区","areaId":"12"},{"name":"昌平区","areaId":"13"},{"name":"大兴区","areaId":"14"},{"name":"怀柔区","areaId":"15"},{"name":"平谷区","areaId":"16"},{"name":"密云县","areaId":"17"},{"name":"延庆县","areaId":"18"}]
             * areaId : 1
             */

            private String name;
            private String areaId;
            private List<AreaListBean> areaList;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAreaId() {
                return areaId;
            }

            public void setAreaId(String areaId) {
                this.areaId = areaId;
            }

            public List<AreaListBean> getAreaList() {
                return areaList;
            }

            public void setAreaList(List<AreaListBean> areaList) {
                this.areaList = areaList;
            }

            public static class AreaListBean {
                /**
                 * name : 东城区
                 * areaId : 1
                 */

                private String name;
                private String areaId;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getAreaId() {
                    return areaId;
                }

                public void setAreaId(String areaId) {
                    this.areaId = areaId;
                }
            }
        }
    }

}
