create table DB_Project.Customer
(
    cid            int auto_increment
        primary key,
    firstName      varchar(20)          not null,
    lastName       varchar(20)          not null,
    billingAddress varchar(50)          not null,
    isDeleted      tinyint(1) default 0 null
);

create table DB_Project.Device
(
    did       int auto_increment
        primary key,
    dtid      int                  not null,
    slid      int                  not null,
    status    tinyint(1)           not null,
    value1    int                  null,
    value2    int                  null,
    isDeleted tinyint(1) default 0 not null,
    constraint Device_DeviceType_dtid_fk
        foreign key (dtid) references db_project.DeviceType (dtid),
    constraint Device_ServiceLocation_slid_fk
        foreign key (slid) references db_project.ServiceLocation (slid)
);

create table DB_Project.DeviceType
(
    dtid      int auto_increment
        primary key,
    name      varchar(50)          not null,
    model     varchar(50)          not null,
    isDeleted tinyint(1) default 0 not null
);

create table DB_Project.EnergyPrice
(
    epid      int auto_increment
        primary key,
    zipcode   char(5)              not null,
    price     decimal(10, 2)       not null,
    time      time                 not null,
    isDeleted tinyint(1) default 0 not null
);

create table DB_Project.Event
(
    eid       int auto_increment
        primary key,
    did       int                            not null,
    label     varchar(50)                    not null,
    value1    int                            null,
    value2    int                            null,
    timestamp time       default (curtime()) not null,
    isDeleted tinyint(1) default 0           not null,
    constraint Event_Device_did_fk
        foreign key (did) references db_project.Device (did)
);

create table DB_Project.ServiceLocation
(
    slid              int auto_increment
        primary key,
    state             char(2)                        not null,
    city              varchar(50)                    not null,
    zipcode           char(5)                        not null,
    address           varchar(50)                    not null,
    squareFootage     int                            not null,
    numberOfBedrooms  int                            not null,
    numberOfOccupants int                            not null,
    isDeleted         tinyint(1) default 0           not null,
    startDate         date       default (curdate()) null,
    cid               int                            not null,
    constraint ServiceLocation_Customer_cid_fk
        foreign key (cid) references db_project.Customer (cid)
);

