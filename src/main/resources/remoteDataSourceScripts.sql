CREATE TABLE Tracking_Device_Commands(
	id integer NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	Tracker_Model varchar(50),
	Tracker_Model_Description varchar(50),
	Command_Title varchar(50),
	Command_Notes varchar(255),
	IMEI_Position integer,
	Command_Syntax varchar(255),
	Trailing_Character varchar(50),
	Fwd_To_Sec_Server integer,
	Sec_Server_IP varchar(50),
	Sec_Server_Port integer);
	
INSERT INTO Tracking_Device_Commands(Tracker_Model,Tracker_Model_Description,Command_Title,Command_Notes,IMEI_Position, Command_Syntax,Trailing_Character, Fwd_To_Sec_Server, Sec_Server_IP, Sec_Server_Port)
VALUES('vl2000','CareTrackerSOS','+RESP:GTBATR','Real Time Battery Request',3,'+RESP:GTBATR,010000,012207000000015,vl2000,,,100,4220,,,20120331070925,0010$','$', 1, '127.0.0.1', 7777);
INSERT INTO Tracking_Device_Commands(Tracker_Model,Tracker_Model_Description,Command_Title,Command_Notes,IMEI_Position, Command_Syntax,Trailing_Character, Fwd_To_Sec_Server, Sec_Server_IP, Sec_Server_Port)
VALUES('vl2000','CareTrackerSOS','+RESP:GTCONF','Real Time Configuration Enquiry',3,'+RESP:GTCONF,010000,012207000000015,vl2000,116.236.221.75,9090,15821885163,3,10,1FE0,60,5,4,0,100,200,1,2,1,1,3,20120331070951,0011$','$', 1, '127.0.0.1', 7777);
INSERT INTO Tracking_Device_Commands(Tracker_Model,Tracker_Model_Description,Command_Title,Command_Notes,IMEI_Position, Command_Syntax,Trailing_Character, Fwd_To_Sec_Server, Sec_Server_IP, Sec_Server_Port)
VALUES('vl2000','CareTrackerSOS','+RESP:GTVER','Version Request Report',3,'+RESP:GTVER,010000,012207000000015,vl2000,,,VL2000B05M64_ST,V04,,,20120331071027,0012$','$', 1, '127.0.0.1', 7777);
INSERT INTO Tracking_Device_Commands(Tracker_Model,Tracker_Model_Description,Command_Title,Command_Notes,IMEI_Position, Command_Syntax,Trailing_Character, Fwd_To_Sec_Server, Sec_Server_IP, Sec_Server_Port)
VALUES('vl2000','CareTrackerSOS','+RESP:GTGSM','GSM Information Request',3,'+RESP:GTGSM,010000,012207000000015,vl2000,460,001,144F,B5F0,00,20,460,001,144F,0E93,460,001,144F,0E94,460,001,144F,2C12,20120331071105,0013$','$', 1, '127.0.0.1', 7777);
