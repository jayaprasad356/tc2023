package com.telugucalendar.telugupanchangamr.Model;

public class MoudyaDinamulu {
    String id,description,title;



       public MoudyaDinamulu(String id, String description, String title) {
            this.id = id;
            this.description = description;
            this.title = title;

        }


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }


        public String getDescription() {
            return description;
        }


        public void setDescription(String description) {
            this.description = description;
        }


        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

}

