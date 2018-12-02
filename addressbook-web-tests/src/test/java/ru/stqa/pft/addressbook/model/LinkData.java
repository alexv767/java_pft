package ru.stqa.pft.addressbook.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "address_in_groups")
public class LinkData {
    public class GroupData {
        @Id
        @Column(name = "id")
        private int contact_id;
        @Column(name = "group_id")
        private int group_id;
    }
}
