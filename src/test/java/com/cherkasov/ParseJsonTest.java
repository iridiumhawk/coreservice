package com.cherkasov;

import com.cherkasov.entities.Device;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ParseJsonTest {

    @Test
    public void jsonParse() {

        String body = "{\"data\":{\"structureChanged\":true,\"updateTime\":1526819960,\"devices\":[{\"creationTime\":1518034540,\"customIcons\":{},\"deviceType\":\"switchControl\",\"h\":-1199109514,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_Remote_5-0-0-B\",\"location\":0,\"metrics\":{\"icon\":\"gesture\",\"level\":\"off\",\"title\":\"Philio Technology Corp (5.0.0) Button\",\"change\":\"\",\"isFailed\":false,\"modificationTime\":1526818376,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526818376},{\"creationTime\":1518355816,\"customIcons\":{},\"deviceType\":\"switchControl\",\"h\":1463401529,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_Remote_8-0-0-B\",\"location\":0,\"metrics\":{\"icon\":\"gesture\",\"level\":\"\",\"title\":\"Vision Security (8.0.0) Button\",\"change\":\"\",\"isFailed\":false,\"modificationTime\":1518355926,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757038},{\"creationTime\":1519238027,\"creatorId\":37,\"customIcons\":{},\"deviceType\":\"toggleButton\",\"h\":1964469784,\"hasHistory\":true,\"id\":\"Notification_37\",\"location\":0,\"metrics\":{\"level\":\"on\",\"icon\":\"gesture\",\"title\":\"Email и СМС Уведомление\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757038},{\"creationTime\":1517941934,\"creatorId\":12,\"customIcons\":{},\"deviceType\":\"toggleButton\",\"h\":-1891043069,\"hasHistory\":true,\"id\":\"MailNotifier_12\",\"location\":0,\"metrics\":{\"level\":\"on\",\"title\":\"Send Email Notification\",\"icon\":\"/ZAutomation/api/v1/load/modulemedia/MailNotifier/icon.png\",\"message\":\"\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"notification_email\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757038},{\"creationTime\":1519080185,\"creatorId\":34,\"customIcons\":{},\"deviceType\":\"toggleButton\",\"h\":557861386,\"hasHistory\":true,\"id\":\"LightScene_34\",\"location\":0,\"metrics\":{\"level\":\"on\",\"icon\":\"scene\",\"title\":\"Сцена OFF socket\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757038},{\"creationTime\":1519080185,\"creatorId\":35,\"customIcons\":{},\"deviceType\":\"toggleButton\",\"h\":557861387,\"hasHistory\":true,\"id\":\"LightScene_35\",\"location\":0,\"metrics\":{\"level\":\"on\",\"icon\":\"scene\",\"title\":\"Off Valve\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757038},{\"creationTime\":1526757038,\"creatorId\":11,\"customIcons\":{},\"deviceType\":\"text\",\"h\":1654191312,\"hasHistory\":false,\"id\":\"MobileAppSupport\",\"location\":0,\"metrics\":{\"title\":\"Mobile App Support\",\"icon\":\"/ZAutomation/api/v1/load/modulemedia/MobileAppSupport/icon.png\",\"text\":\"\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":true,\"probeType\":\"\",\"tags\":[],\"visibility\":false,\"updateTime\":1526757038},{\"creationTime\":1517941934,\"creatorId\":7,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":-592588978,\"hasHistory\":false,\"id\":\"BatteryPolling_7\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"title\":\"Battery Polling\",\"level\":70,\"modificationTime\":1526775431,\"lastLevel\":70},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526819064},{\"creationTime\":1517941934,\"creatorId\":5,\"customIcons\":{},\"deviceType\":\"text\",\"h\":-1261400328,\"hasHistory\":false,\"id\":\"InfoWidget_5_Int\",\"location\":0,\"metrics\":{\"title\":\"Dear Expert User\",\"text\":\"<center>If you still want to use ExpertUI please go, after you are successfully logged in, to <br><strong> Menu > Devices > Manage with ExpertUI </strong> <br> or call <br><strong> http://MYRASP:8083/expert </strong><br> in your browser. <br> <br>You could hide or remove this widget in menu <br><strong>Apps > Active Tab</strong>. </center>\",\"icon\":\"app/img/logo-z-wave-z-only.png\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526816433},{\"creationTime\":1517941934,\"creatorId\":10,\"customIcons\":{},\"deviceType\":\"text\",\"h\":-606006820,\"hasHistory\":false,\"id\":\"InfoWidget_10_Int\",\"location\":0,\"metrics\":{\"title\":\"Cloud Backup Instructions\",\"text\":\"<center>Cloud backup is conveniently saving up to 3 backup files on our server (using SSL encryption).<br>By default, an automatic backup is created every month on 28 at 23:59.<br>If you don’t like to see your backup file on our server, just deactivate this service or change the interval.</br>To change the settings, please click on<br><strong>Menu > Management > Backup & Restore.</strong><br><br>You could hide or remove this widget in<br><strong>Menu >Apps > Active Tab</strong>.</center>\",\"icon\":\"app/img/icon_cloudbackup.png\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526816433},{\"creationTime\":1519237524,\"creatorId\":22,\"customIcons\":{},\"deviceType\":\"sensorMultilevel\",\"h\":1247732929,\"hasHistory\":true,\"id\":\"JSONDevice_22\",\"location\":0,\"metrics\":{\"scaleTitle\":\"C\",\"title\":\"JSONtest\",\"probeTitle\":\"InsideTemp\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518273074,\"creatorId\":21,\"customIcons\":{},\"deviceType\":\"sensorMultiline\",\"h\":1777987625,\"hasHistory\":false,\"id\":\"LeakageProtection_21\",\"location\":0,\"metrics\":{\"title\":\"Leakage Protection\",\"sensors\":[{\"id\":\"ZWayVDev_zway_9-0-48-6\",\"deviceType\":\"sensorBinary\",\"metrics\":{\"probeTitle\":\"Water\",\"scaleTitle\":\"\",\"icon\":\"flood\",\"level\":\"off\",\"title\":\"Shenzhen Neo Electronics Co., Ltd Water (#9)\",\"isFailed\":false,\"modificationTime\":1518347654,\"lastLevel\":\"off\"},\"hasHistory\":true,\"updateTime\":1526757039}],\"multilineType\":\"protection\",\"icon\":\"/ZAutomation/api/v1/load/modulemedia/LeakageProtection/ok.png\",\"level\":\"OK\",\"state\":\"armed\",\"modificationTime\":1519080185,\"lastLevel\":\"OK\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518034474,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":-2058221405,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_5-0-48-8\",\"location\":0,\"metrics\":{\"probeTitle\":\"Tamper\",\"scaleTitle\":\"\",\"icon\":\"tamper\",\"level\":\"on\",\"title\":\"Tamper  Philio Technology Corp Tamper (#5)\",\"isFailed\":false,\"modificationTime\":1524951317,\"lastLevel\":\"on\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"tamper\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526773888},{\"creationTime\":1518034474,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":619645716,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_5-0-48-10\",\"location\":0,\"metrics\":{\"probeTitle\":\"Door/Window\",\"scaleTitle\":\"\",\"icon\":\"door\",\"level\":\"on\",\"title\":\"Door Philio Technology Corp Door/Window (#5)\",\"isFailed\":false,\"modificationTime\":1526772873,\"lastLevel\":\"on\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"door-window\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526819064},{\"creationTime\":1518034474,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":619645718,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_5-0-48-12\",\"location\":0,\"metrics\":{\"probeTitle\":\"Motion\",\"scaleTitle\":\"\",\"icon\":\"motion\",\"level\":\"off\",\"title\":\"Motion  Philio Technology Corp Motion (#5)\",\"isFailed\":false,\"modificationTime\":1526818360,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"motion\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526818360},{\"creationTime\":1518034474,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorMultilevel\",\"h\":-2058220451,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_5-0-49-1\",\"location\":0,\"metrics\":{\"probeTitle\":\"Temperature\",\"scaleTitle\":\"°C\",\"level\":27.2,\"icon\":\"temperature\",\"title\":\"Temperature  Philio Technology Corp Temperature (#5)\",\"isFailed\":false,\"modificationTime\":1526818253,\"lastLevel\":27.2},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":2},\"permanently_hidden\":false,\"probeType\":\"temperature\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526819064},{\"creationTime\":1518034474,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorMultilevel\",\"h\":-2058220449,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_5-0-49-3\",\"location\":0,\"metrics\":{\"probeTitle\":\"Luminiscence\",\"scaleTitle\":\"%\",\"level\":25,\"icon\":\"luminosity\",\"title\":\"Luminiscence Philio Technology Corp Luminiscence (#5)\",\"isFailed\":false,\"modificationTime\":1526819064,\"lastLevel\":25},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":1},\"permanently_hidden\":false,\"probeType\":\"luminosity\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526819064},{\"creationTime\":1518034472,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":1734718019,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_5-0-128\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"level\":100,\"icon\":\"battery\",\"title\":\"Philio Technology Corp Battery (#5)\",\"isFailed\":false,\"modificationTime\":1518039971,\"lastLevel\":100},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526819064},{\"creationTime\":1518214805,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":1121732254,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_6-0-113-2-2-A\",\"location\":1,\"metrics\":{\"icon\":\"alarm_co\",\"level\":\"off\",\"title\":\"Vision Security CO Alarm (#6)\",\"isFailed\":false},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0,\"room\":1},\"permanently_hidden\":false,\"probeType\":\"alarm_co\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1519482380,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":1126350820,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_6-0-113-7-3-A\",\"location\":0,\"metrics\":{\"icon\":\"alarm_burglar\",\"level\":\"off\",\"title\":\"Vision Security Burglar Alarm (#6)\",\"isFailed\":false},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"alarm_burglar\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518214600,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":-1672745596,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_6-0-128\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"level\":70,\"icon\":\"battery\",\"title\":\"Vision Security Battery (#6)\",\"isFailed\":false,\"modificationTime\":1524863439,\"lastLevel\":70},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215313,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":-1124757703,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_8-0-48-1\",\"location\":0,\"metrics\":{\"probeTitle\":\"General purpose\",\"scaleTitle\":\"\",\"icon\":\"motion\",\"level\":\"off\",\"title\":\"Vision Security General purpose (#8)\",\"isFailed\":false,\"modificationTime\":1518215313,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"general_purpose\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215313,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":102261766,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_8-0-128\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"level\":100,\"icon\":\"battery\",\"title\":\"Vision Security Battery (#8)\",\"isFailed\":false,\"modificationTime\":1518215313,\"lastLevel\":100},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215468,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":618052637,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_9-0-48-6\",\"location\":0,\"metrics\":{\"probeTitle\":\"Water\",\"scaleTitle\":\"\",\"icon\":\"flood\",\"level\":\"off\",\"title\":\"Shenzhen Neo Electronics Co., Ltd Water (#9)\",\"isFailed\":false,\"modificationTime\":1518347654,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"flood\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215469,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"sensorBinary\",\"h\":244293796,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_9-0-113-5-2-A\",\"location\":0,\"metrics\":{\"icon\":\"alarm_flood\",\"level\":\"off\",\"title\":\"Shenzhen Neo Electronics Co., Ltd Water Alarm (#9)\",\"isFailed\":false,\"modificationTime\":1518347653,\"lastLevel\":\"off\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"alarm_flood\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215468,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":989765447,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_9-0-128\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"level\":100,\"icon\":\"battery\",\"title\":\"Shenzhen Neo Electronics Co., Ltd Battery (#9)\",\"isFailed\":false,\"modificationTime\":1518215468,\"lastLevel\":100},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518215879,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"switchBinary\",\"h\":-1732094256,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_10-0-37\",\"location\":0,\"metrics\":{\"icon\":\"switch\",\"title\":\"Valve UFairy G.R. Tech Switch (#10)\",\"isFailed\":true,\"level\":\"on\",\"modificationTime\":1518355056,\"lastLevel\":\"on\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518266283,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"battery\",\"h\":-1267812724,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_11-0-128\",\"location\":0,\"metrics\":{\"probeTitle\":\"Battery\",\"scaleTitle\":\"%\",\"level\":94,\"icon\":\"battery\",\"title\":\"Vision Security Battery (#11)\",\"isFailed\":false,\"modificationTime\":1518814802,\"lastLevel\":94},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1518266285,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"doorlock\",\"h\":-1703464918,\"hasHistory\":true,\"id\":\"ZWayVDev_zway_11-0-98\",\"location\":1,\"metrics\":{\"level\":\"open\",\"icon\":\"door\",\"title\":\"Vision Security Door Lock (#11)\",\"isFailed\":false,\"modificationTime\":1518373760,\"lastLevel\":\"open\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0,\"room\":2},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[\"store\"],\"visibility\":true,\"updateTime\":1526757039},{\"creationTime\":1520763187,\"creatorId\":1,\"customIcons\":{},\"deviceType\":\"switchBinary\",\"h\":-1674835954,\"hasHistory\":false,\"id\":\"ZWayVDev_zway_12-0-37\",\"location\":1,\"metrics\":{\"icon\":\"switch\",\"title\":\"TKB Home Switch (#12)\",\"isFailed\":false,\"level\":\"on\",\"modificationTime\":1526817158,\"lastLevel\":\"on\"},\"order\":{\"rooms\":0,\"elements\":0,\"dashboard\":0,\"room\":2},\"permanently_hidden\":false,\"probeType\":\"\",\"tags\":[],\"visibility\":true,\"updateTime\":1526817158}]},\"code\":200,\"message\":\"200 OK\",\"error\":null}";


        List<Device> devices = new ArrayList<>();

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = null;
        try {
            root = mapper.readTree(body);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonNode jsonNode = root.get("data").get("devices");
//        JsonNode uuid = root.path("value");
        Iterator<JsonNode> iterator = jsonNode.iterator();
        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
            JsonNode node = iterator.next();
            Device device = new Device();
            device.setDeviceTypeString(node.get("deviceType").asText());
            device.setName(node.get("id").asText());
            JsonNode metrics = node.get("metrics");
            device.setGivenName(metrics.get("title").asText());
            device.setIsFailed(metrics.path("isFailed").asBoolean());
            device.setProbeTitle(metrics.path("probeTitle").asText());
            device.setModificationTime(metrics.path("modificationTime").asLong());
//            device.setLastReceived();
//            device.setLastSend();

            if (device.getName() == null || device.getName().isEmpty()) {
                System.out.println("NULL");
            }
            devices.add(device);
        }

        System.out.println(devices);
    }
}
