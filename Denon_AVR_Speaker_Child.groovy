def TYPE = "Speakers"
def NAME = "Denon AVR Speakers Child Device"
def NAME_SPACE = "Denon AVR";

metadata {
    
    definition(name: NAME, namespace: NAME_SPACE, author: "Thomas Howard") {        
		capability "Actuator"
        
        deviceSetup(TYPE); 
      
    }
    
    preferences {
    }
    
}

def deviceSetup(device_name){
        
    command "executeCommand",["command"]

    deviceCommands = getDeviceCommands();                

    deviceCommands."$device_name".each{ func, choices -> 
        if (choices instanceof Map){
            def val = false;


            //if a capability is defined, add it
            if (choices.capability){
                capability "${choices.capability}"
            } else {

                def list = choices.commands.collect{ 
                    key, element ->    

                    if (element instanceof Map) {
                        if (element.val == true) val = true;
                        return element.name;
                    }
                    else return null;        
                }.findAll { e ->
                    return (boolean) e;
                }

                if (val){ 
                    params = [
                        [name: "${func}",
                         description: "Set the ${device} ${func}",
                         constraints: list,
                         type: "ENUM"
                        ],
                        [name: "value",
                         description: "Set the ${device} ${func}",
                         type: "NUMBER"   
                        ]
                    ];
                } else {
                    params = [
                        [name: "${func}",
                         method: "blah",
                         description: "Set the ${device} ${func}",
                         constraints: list,
                         type: "ENUM"
                        ]
                    ];
                }

                //Set the command
                command "Set${func}", params

                attribute "${func}", "string"

            }

        }
    }
}

def getDeviceCommands(){
    
    command_refresh = [
        refresh:     [command: "?",     name: "Refresh", val: false, delay: 1, capability: "Refresh", capability_func: "refresh", capability_value: "refresh"],
    ]
    
    command_level = [
        auto:     [command: "AUTO",     name: "Auto",     val: false, delay: 1],
        low:      [command: "LOW",      name: "Low",      val: false, delay: 1],
        mid:      [command: "MID",      name: "Mid",      val: false, delay: 1],
        high:     [command: "HIGH",     name: "High",     val: false, delay: 1],
        off:      [command: "OFF",      name: "Off",      val: false, delay: 1],
    ];
    
    command_on_standby = [ 
        on:     [command: "ON",      name: "On",      val: false, delay: 10, capability: "Switch", capability_func: "on", capability_value: "on"],
        off:    [command: "STANDBY", name: "Off",     val: false, delay: 1,  capability: "Switch", capability_func: "off", capability_value: "off"],
    ];

    command_on_off =     [
        on:     [command: "ON",      name: "On",       val: false, delay: 1, capability: "Switch", capability_func: "on", capability_value: "on"],
        off:    [command: "OFF",     name: "Off",      val: false, delay: 1, capability: "Switch", capability_func: "off", capability_value: "off"],   
    ];
    
    command_mute =     [
        on:     [command: "ON",      name: "On",       val: false, delay: 1, capability: "AudioVolume", capability_func: "mute", capability_value: "muted"],
        off:    [command: "OFF",     name: "Off",      val: false, delay: 1, capability: "AudioVolume", capability_func: "unmute", capability_value: "unmuted"],   
    ];
    
    command_volume =   [
        up:      [command: "UP",      name: "Up",       val: false, delay: 1, capability: "AudioVolume", capability_func: "volumeUp", capability_value: "volume"],
        down:    [command: "DOWN",    name: "Down",     val: false, delay: 1, capability: "AudioVolume", capability_func: "volumeDown", capability_value: "volume"],
        set:     [command: "",        name: "Set",      val: true,  delay: 1, capability: "AudioVolume", capability_func: "setVolume", capability_value: "volume", capability_vars: 1],
    ];

    command_up_down = [
        up:      [command: "UP",      name: "Up",       val: false, delay: 1],
        down:    [command: "DOWN",    name: "Down",     val: false, delay: 1],
        set:     [command: "",        name: "Set",      val: true,  delay: 1],
    ];

    command_input = [
        tuner:   [command: "TUNER",   name: "Tuner",           val: false, delay: 1],
        dvd:     [command: "DVD",     name: "DVD",             val: false, delay: 1], 
        blueray: [command: "BD",      name: "BlueRay",         val: false, delay: 1],
        tv:      [command: "TV",      name: "TV",              val: false, delay: 1],
        sat_cbl: [command: "SAT/CBL", name: "Satellite/Cable", val: false, delay: 1],
        mplay:   [command: "MPLAY",   name: "Media Player",    val: false, delay: 1],
        game:    [command: "GAME",    name: "Game",            val: false, delay: 1],
        aux:     [command: "AUX1",    name: "Auxilary",        val: false, delay: 1],
        network: [command: "NET",     name: "Network",         val: false, delay: 1],
    ];
    
    video_select = [
        off:     [command: "OFF",     name: "Off",             val: false, delay: 1],
        dvd:     [command: "DVD",     name: "DVD",             val: false, delay: 1], 
        blueray: [command: "BD",      name: "BlueRay",         val: false, delay: 1],
        tv:      [command: "TV",      name: "TV",              val: false, delay: 1],
        sat_cbl: [command: "SAT/CBL", name: "Satellite/Cable", val: false, delay: 1],
        mplay:   [command: "MPLAY",   name: "Media Player",    val: false, delay: 1],
        game:    [command: "GAME",    name: "Game",            val: false, delay: 1],
        vaux:    [command: "V.AUX",   name: "Auxilary",        val: false, delay: 1],
        network: [command: "NET",     name: "Network",         val: false, delay: 1],
        dock:    [command: "DOCK",    name: "Dock",            val: false, delay: 1],
        source:  [command: "SOURCE",  name: "Source",          val: false, delay: 1],

    ];

    command_mode = [
        movie:        [command: "MOVIE",         name: "Movie",                val: false, delay: 1],
        music:        [command: "MUSIC",         name: "Music",                val: false, delay: 1], 
        game:         [command: "GAME",          name: "Game",                 val: false, delay: 1],
        direct:       [command: "DIRECT",        name: "Direct",               val: false, delay: 1],
        pure_direct:  [command: "PURE DIRECT",   name: "Pure Direct",          val: false, delay: 1],
        stereo:       [command: "STEREO",        name: "Stereo",               val: false, delay: 1],
        standard:     [command: "STANDARD",      name: "Standard",             val: false, delay: 1],
        dolby_digital:[command: "DOLBY DIGITAL", name: "Dolby Digital",        val: false, delay: 1],
        dts_surround: [command: "DTS SUROUND",   name: "DTS Surround Sound",   val: false, delay: 1],
        mch_stero:    [command: "MCH STEREO",    name: "Multi-Channel Stereo", val: false, delay: 1],
    ]; 
    
    command_audyssey = [
        audyssey:     [command: "AUDYSSEY",         name: "On",                         val: false, delay: 1],   
        byp_lr:       [command: "BYP.LR",           name: "Bypass",                     val: false, delay: 1],
        flat:         [command: "FLAT",             name: "Flat",                       val: false, delay: 1],
        manual:       [command: "MANUAL",           name: "Manual",                     val: false, delay: 1],
        off:          [command: "OFF",              name: "Off",                        val: false, delay: 1],
    ];
    
    requirements_tone = [
        [var: "Tone",         val: "On"],
        [var: "AudioMode",    not: "Direct"],
        [var: "AudioMode",    not: "Pure Direct"]
    ];

    deviceCommands = [ 
        Main: 
        [ 
            name: "Main",
            Power:  [
                name: "Power", prefix: "PW", status: true,
                commands: command_on_standby,
                capability: "Switch", capability_var: "switch"
            ],

            Volume: [
                name: "Volume", prefix: "MV", status: true,
                commands: command_volume,
                capability: "AudioVolume", capability_var: "volume"
            ],
            
            Mute:  [   
                name: "Mute", prefix: "MU", status: true,
                commands: command_mute,
                capability: "AudioVolume", capability_var: "mute"
            ],

            Input:  [ 
                name: "Input", prefix: "SI", status: true,
                commands: command_input
            ],

            AudioMode: [ 
                name: "Audio Mode", prefix: "MS", status: true,
                commands: command_mode
            ],
            Select: [
                name: "Video Select", prefix: "SV", status: true,
                commands: video_select
            ],  
            Tone: [
                name: "Tone Control", prefix: "PSTONE CTRL ", status: true,
                commands: command_on_off
            ],
            Bass: [
                name: "Bass", prefix: "PSBAS ", status: true,
                commands: command_up_down,
                requirements: requirements_tone
            ],
            Treble: [
                name: "Treble", prefix: "PSTRE ", status: true,
                commands: command_up_down,
                requirements: requirements_tone
            ],
            LowFrequencyEffect: [
                name: "Low Frequency Effect", prefix: "PSLFE ", status: true,
                commands: command_up_down
            ],
            DirectChange: [
                name: "Direct Change", prefix: "PSDRC ", status: true,
                commands: command_level  
            ],
            AudysseyMode: [
                name: "Audyssey", prefix: "PSMULTEQ:", status: true,
                commands: command_audyssey
            ],
            MainZone: [
                name: "Main Zone", prefix: "ZM", status: true,
                commands: command_on_off
            ],
            DynamicEqualizer: [
               name: "Dynamic Equalizer", prefix: "PSDYNEQ ", status: true,
               commands: command_on_off,
            ],
        ],                            
        
        Zone2: [ 
            name: "Zone 2", child_device: true, child_type: "AVR Zone 2 Child Device", child_namespace: "Denon AVR", child_var: "zone2",
            Power:  [
                name: "Power", prefix: "Z2", status: true,
                commands: command_on_off,
                capability: "Switch", capability_var: "switch"

            ],
            Input:  [ 
                name: "Input", prefix: "Z2", status: false,
                commands: command_input
            ],
            Volume: [
                name: "Volume", prefix: "Z2", status: true,
                commands: command_volume,
                capability: "AudioVolume", capability_var: "volume"
            ],
            
            Mute:  [   
                name: "Mute", prefix: "Z2MU", status: true,
                commands: command_mute,
                capability: "AudioVolume", capability_var: "mute"
            ],
        ],
        Speakers: [
            name: "Speakers", child_device: true, child_type: "AVR Speakers Child Device", child_namespace: "Denon AVR", child_var: "speakers",
            
            Front_Left:             [name: "Front Left Speaker",          prefix: "CVFL ",  status: false, commands: command_up_down],
            Front_Right:            [name: "Front Right Speaker",         prefix: "CVFR ",  status: false, commands: command_up_down],
            Center:                 [name: "Center Speaker",              prefix: "CVC ",   status: false, commands: command_up_down],
            Subwoofer:              [name: "Subwoofer",                   prefix: "CVSW ",  status: false, commands: command_up_down],
            Surround_Left:          [name: "Surround Left Speaker",       prefix: "CVSL ",  status: false, commands: command_up_down],
            Surround_Right:         [name: "Surround Right Speaker",      prefix: "CVSR ",  status: false, commands: command_up_down],
            Surround_Left_Back:     [name: "Surround Left Back Speaker",  prefix: "CVSBL ", status: false, commands: command_up_down],
            Surround_Right_Back:    [name: "Surround Right Back Speaker", prefix: "CVSBR ", status: false, commands: command_up_down],
           
            Front_Height_Left:     [name: "Front Height Left Speaker",  prefix: "CVFHL ", status: false, commands: command_up_down],
            Front_Height_Right:    [name: "Front Height Right Speaker", prefix: "CVFHR ", status: false, commands: command_up_down],
            Front_Wide_Left:     [name: "Front Wide Left Speaker",  prefix: "CVFWL ", status: false, commands: command_up_down],
            Front_Wide_Right:    [name: "Front Wide Right Speaker", prefix: "CVFWR ", status: false, commands: command_up_down],
            Top_Front_Left:     [name: "Top Front Left Speaker",  prefix: "CVTFL ", status: false, commands: command_up_down],
            Top_Front_Right:    [name: "Top Front Right Speaker", prefix: "CVTFR ", status: false, commands: command_up_down],
            Top_Middle_Left:     [name: "Top Middle Left Speaker",  prefix: "CVTML ", status: false, commands: command_up_down],
            Top_Middle_Right:    [name: "Top Middle Right Speaker", prefix: "CVTMR ", status: false, commands: command_up_down],
            Top_Rear_Left:     [name: "Top Rear Left Speaker",  prefix: "CVTRL ", status: false, commands: command_up_down],
            Top_Rear_Right:    [name: "Top Rear Right Speaker", prefix: "CVTRR ", status: false, commands: command_up_down],
            Rear_Height_Left:     [name: "Rear Height Left Speaker",  prefix: "CVRHL ", status: false, commands: command_up_down],
            Rear_Height_Right:    [name: "Rear Height Right Speaker", prefix: "CVRHR ", status: false, commands: command_up_down],
            Front_Dolby_Left:     [name: "Front Dolby Left Speaker",  prefix: "CVFDL ", status: false, commands: command_up_down],
            Front_Dolby_Right:    [name: "Front DOlby Speaker", prefix: "CVFDR ", status: false, commands: command_up_down],
            Surround_Dolby_Left:     [name: "Surround Dolby Left Speaker",  prefix: "CVSDL ", status: false, commands: command_up_down],
            Surround_Dolby_Right:    [name: "Surround Dolby Right Speaker", prefix: "CVSDR ", status: false, commands: command_up_down],
            Back_Dolby_Left:     [name: "Back Dolby Left Speaker",  prefix: "CVBDL ", status: false, commands: command_up_down],
            Back_Dolby_Right:    [name: "Back Dolby Right Speaker", prefix: "CVBDR ", status: false, commands: command_up_down],
            Surround_Height_Left:     [name: "Surround Height Left Speaker",  prefix: "CVCHL ", status: false, commands: command_up_down],
            Surround_Height_Right:    [name: "Surround Height Right Speaker", prefix: "CVCHR ", status: false, commands: command_up_down],
            Top_Surround:     [name: "Top Surround Speaker",  prefix: "CVTS ", status: false, commands: command_up_down],
            Center_Height:    [name: "Center Height Speaker", prefix: "CVCH ", status: false, commands: command_up_down],
            Subwoofer_2:     [name: "Subwoofer 2",  prefix: "CVSW2 ", status: false, commands: command_up_down],
            Surround_Back_1_Spreaker:    [name: "Surround Back 1 Speaker", prefix: "CVSB ", status: false, commands: command_up_down],

            
        ],
    ]; 
    return deviceCommands;
}


def methodMissing(String name, Object args) {
    
    if (device){
        type = device.getDataValue("type");
        function = parent.getFunction(name, args, type);
    }
    if (function != null) {
        return function;
    } else {
        //super
        def method = this.class.metaClass.pickMethod("invokeMethod", [String, Object] as Class[]);
        return method.invoke(this, name, args);
    }
}


