USE herosightingdb;
INSERT INTO Location VALUES (1,'Hero Academy','Tokyo Central','Tokyo, Japan',51.501504,-0.141796),(2,'Hero Square','In the middle of Osaka lake','West North, Japan',51.510550,-0.130080);
INSERT INTO Organisation VALUES (1,'Villains Association','Bar for the despite','Deep Underground',"45326680"),(2,'Hero Central','Best Heros','Kyoto, Japan',"704231782"),(3,'Super EU','For everyone with superpowers in EU','Hitoyo HQ',"32439821300");
INSERT INTO Hero VALUES (1,'All Might','All for one', 'Leader of superheros'),(2,'All For One', 'Devil', 'Faceless Gentleman');
INSERT INTO HeroOrganisation VALUES (2,1),(3,1),(1,2);
INSERT INTO Sighting VALUES (1,2,'2022-09-13', 2),(2,1,'2022-06-08',1);