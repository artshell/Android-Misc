package com.artshell.arch.storage.server.model;

import com.artshell.arch.storage.server.paging.HttpPagingResult;

public class HouseResponse extends HttpPagingResult<HouseResponse.HouseEntity> {
    public static class HouseEntity {
        private String id;
        private String city_id;
        private String country_id;
        private String company_id;
        private String is_vip;
        private String is_hot;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getCountry_id() {
            return country_id;
        }

        public void setCountry_id(String country_id) {
            this.country_id = country_id;
        }

        public String getCompany_id() {
            return company_id;
        }

        public void setCompany_id(String company_id) {
            this.company_id = company_id;
        }

        public String getIs_vip() {
            return is_vip;
        }

        public void setIs_vip(String is_vip) {
            this.is_vip = is_vip;
        }

        public String getIs_hot() {
            return is_hot;
        }

        public void setIs_hot(String is_hot) {
            this.is_hot = is_hot;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
