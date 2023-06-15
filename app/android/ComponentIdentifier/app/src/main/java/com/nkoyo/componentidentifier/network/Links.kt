package com.nkoyo.componentidentifier.network

val linker: HashMap<String, Pair<String, String>> = hashMapOf(
    "resistor" to Pair(
        "https://en.wikipedia.org/wiki/Resistor",
        "A resistor is a passive two-terminal electrical component that implements electrical resistance as a circuit element. In electronic circuits, resistors are used to reduce current flow, adjust signal levels, to divide voltages, bias active elements, and terminate transmission lines, among other uses. High-power resistors that can dissipate many watts of electrical power as heat may be used as part of motor controls, in power distribution systems, or as test loads for generators. Fixed resistors have resistances that only change slightly with temperature, time or operating voltage. Variable resistors can be used to adjust circuit elements (such as a volume control or a lamp dimmer), or as sensing devices for heat, light, humidity, force, or chemical activity."
    ),

    "capacitor" to Pair(
        "https://en.wikipedia.org/wiki/Capacitor",
        "A capacitor is a device that stores electrical energy in an electric field by virtue of accumulating electric charges on two close surfaces insulated from each other. It is a passive electronic component with two terminals.\n" +
                "\n" +
                "The effect of a capacitor is known as capacitance. While some capacitance exists between any two electrical conductors in proximity in a circuit, a capacitor is a component designed to add capacitance to a circuit. The capacitor was originally known as the condenser,[1] a term still encountered in a few compound names, such as the condenser microphone."
    ),

    "diode" to Pair(
        "https://en.wikipedia.org/wiki/Diode",
        "A diode is a two-terminal electronic component that conducts current primarily in one direction (asymmetric conductance). It has low (ideally zero) resistance in one direction, and high (ideally infinite) resistance in the other.\n" +
                "\n" +
                "A semiconductor diode, the most commonly used type today, is a crystalline piece of semiconductor material with a p–n junction connected to two electrical terminals.[4] It has an exponential current–voltage characteristic. Semiconductor diodes were the first semiconductor electronic devices. The discovery of asymmetric electrical conduction across the contact between a crystalline mineral and a metal was made by German physicist Ferdinand Braun in 1874. Today, most diodes are made of silicon, but other semiconducting materials such as gallium arsenide and germanium are also used."
    ),

    "ic" to Pair(
        "https://en.wikipedia.org/wiki/Integrated_circuit",
        "An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, usually silicon. Large numbers of miniaturized transistors and other electronic components are integrated together on the chip. This results in circuits that are orders of magnitude smaller, faster, and less expensive than those constructed of discrete components, allowing a large transistor count. The IC's mass production capability, reliability, and building-block approach to integrated circuit design has ensured the rapid adoption of standardized ICs in place of designs using discrete transistors. ICs are now used in virtually all electronic equipment and have revolutionized the world of electronics. Computers, mobile phones and other home appliances are now inextricable parts of the structure of modern societies, made possible by the small size and low cost of ICs such as modern computer processors and microcontrollers."
    ),

    "inductor" to Pair(
        "https://en.wikipedia.org/wiki/Inductor",
        "An inductor, also called a coil, choke, or reactor, is a passive two-terminal electrical component that stores energy in a magnetic field when electric current flows through it.[1] An inductor typically consists of an insulated wire wound into a coil.\n" +
                "\n" +
                "When the current flowing through the coil changes, the time-varying magnetic field induces an electromotive force (emf) (voltage) in the conductor, described by Faraday's law of induction. According to Lenz's law, the induced voltage has a polarity (direction) which opposes the change in current that created it. As a result, inductors oppose any changes in current through them."
    ),

    "transformer" to Pair(
        "https://en.wikipedia.org/wiki/Transformer",
        "A transformer is a passive component that transfers electrical energy from one electrical circuit to another circuit, or multiple circuits. A varying current in any coil of the transformer produces a varying magnetic flux in the transformer's core, which induces a varying electromotive force (EMF) across any other coils wound around the same core. Electrical energy can be transferred between separate coils without a metallic (conductive) connection between the two circuits. Faraday's law of induction, discovered in 1831, describes the induced voltage effect in any coil due to a changing magnetic flux encircled by the coil."
    )
)

val linker2: HashMap<String, Pair<String, String>> = hashMapOf(
    "battries" to Pair(
        "https://en.wikipedia.org/wiki/Electric_battery",
        "A battery is a source of electric power consisting of one or more electrochemical cells with external connections[1] for powering electrical devices. When a battery is supplying power, its positive terminal is the cathode and its negative terminal is the anode.[2] The terminal marked negative is the source of electrons that will flow through an external electric circuit to the positive terminal. When a battery is connected to an external electric load, a redox reaction converts high-energy reactants to lower-energy products, and the free-energy difference is delivered to the external circuit as electrical energy. Historically the term \"battery\" specifically referred to a device composed of multiple cells; however, the usage has evolved to include devices composed of a single cell.[3]"
    ),
    "capacitor" to Pair(
        "https://en.wikipedia.org/wiki/Capacitor",
        "A capacitor is a device that stores electrical energy in an electric field by virtue of accumulating electric charges on two close surfaces insulated from each other. It is a passive electronic component with two terminals.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"The effect of a capacitor is known as capacitance. While some capacitance exists between any two electrical conductors in proximity in a circuit, a capacitor is a component designed to add capacitance to a circuit. The capacitor was originally known as the condenser,[1] a term still encountered in a few compound names, such as the condenser microphone."
    ),
    "cartridge_fuse" to Pair(
        "https://en.wikipedia.org/wiki/Fuse_(electrical)",
        "In electronics and electrical engineering, a fuse is an electrical safety device that operates to provide overcurrent protection of an electrical circuit. Its essential component is a metal wire or strip that melts when too much current flows through it, thereby stopping or interrupting the current. It is a sacrificial device; once a fuse has operated it is an open circuit, and must be replaced or rewired, depending on its type."
    ),
    "circuit_breaker" to Pair(
        "https://en.wikipedia.org/wiki/Circuit_breaker",
        "A circuit breaker is an electrical safety device designed to protect an electrical circuit from damage caused by overcurrent. Its basic function is to interrupt current flow to protect equipment and to prevent the risk of fire. Unlike a fuse, which operates once and then must be replaced, a circuit breaker can be reset (either manually or automatically) to resume normal operation."
    ),
    "filament_bulb" to Pair(
        "https://en.wikipedia.org/wiki/Incandescent_light_bulb",
        "An incandescent light bulb, incandescent lamp or incandescent light globe is an electric light with a wire filament heated until it glows. The filament is enclosed in a glass bulb with a vacuum or inert gas to protect the filament from oxidation. Current is supplied to the filament by terminals or wires embedded in the glass. A bulb socket provides mechanical support and electrical connections."
    ),
    "inductor" to Pair(
        "https://en.wikipedia.org/wiki/Inductor",
        "An inductor, also called a coil, choke, or reactor, is a passive two-terminal electrical component that stores energy in a magnetic field when electric current flows through it.[1] An inductor typically consists of an insulated wire wound into a coil.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"When the current flowing through the coil changes, the time-varying magnetic field induces an electromotive force (emf) (voltage) in the conductor, described by Faraday's law of induction. According to Lenz's law, the induced voltage has a polarity (direction) which opposes the change in current that created it. As a result, inductors oppose any changes in current through them."
    ),
    "led" to Pair(
        "https://en.wikipedia.org/wiki/Light-emitting_diode",
        "A light-emitting diode (LED) is a semiconductor device that emits light when current flows through it. Electrons in the semiconductor recombine with electron holes, releasing energy in the form of photons. The color of the light (corresponding to the energy of the photons) is determined by the energy required for electrons to cross the band gap of the semiconductor.[5] White light is obtained by using multiple semiconductors or a layer of light-emitting phosphor on the semiconductor device.[6]"
    ),
    "resistor" to Pair(
        "https://en.wikipedia.org/wiki/Resistor",
        "A resistor is a passive two-terminal electrical component that implements electrical resistance as a circuit element. In electronic circuits, resistors are used to reduce current flow, adjust signal levels, to divide voltages, bias active elements, and terminate transmission lines, among other uses. High-power resistors that can dissipate many watts of electrical power as heat may be used as part of motor controls, in power distribution systems, or as test loads for generators. Fixed resistors have resistances that only change slightly with temperature, time or operating voltage. Variable resistors can be used to adjust circuit elements (such as a volume control or a lamp dimmer), or as sensing devices for heat, light, humidity, force, or chemical activity."
    ),
    "transformer" to Pair(
        "https://en.wikipedia.org/wiki/Transformer",
        "A transformer is a passive component that transfers electrical energy from one electrical circuit to another circuit, or multiple circuits. A varying current in any coil of the transformer produces a varying magnetic flux in the transformer's core, which induces a varying electromotive force (EMF) across any other coils wound around the same core. Electrical energy can be transferred between separate coils without a metallic (conductive) connection between the two circuits. Faraday's law of induction, discovered in 1831, describes the induced voltage effect in any coil due to a changing magnetic flux encircled by the coil."
    ),
    "transistor" to Pair(
        "https://en.wikipedia.org/wiki/Transistor",
        "A transistor is a semiconductor device used to amplify or switch electrical signals and power. It is one of the basic building blocks of modern electronics.[1] It is composed of semiconductor material, usually with at least three terminals for connection to an electronic circuit. A voltage or current applied to one pair of the transistor's terminals controls the current through another pair of terminals. Because the controlled (output) power can be higher than the controlling (input) power, a transistor can amplify a signal. Some transistors are packaged individually, but many more in miniature form are found embedded in integrated circuits."
    )
)

val linker3: HashMap<String, Pair<String, String>> = hashMapOf(
    "battries" to Pair(
        "https://en.wikipedia.org/wiki/Electric_battery",
        "A battery is a source of electric power consisting of one or more electrochemical cells with external connections[1] for powering electrical devices. When a battery is supplying power, its positive terminal is the cathode and its negative terminal is the anode.[2] The terminal marked negative is the source of electrons that will flow through an external electric circuit to the positive terminal. When a battery is connected to an external electric load, a redox reaction converts high-energy reactants to lower-energy products, and the free-energy difference is delivered to the external circuit as electrical energy. Historically the term \"battery\" specifically referred to a device composed of multiple cells; however, the usage has evolved to include devices composed of a single cell.[3]"
    ),
    "capacitor" to Pair(
        "https://en.wikipedia.org/wiki/Capacitor",
        "A capacitor is a device that stores electrical energy in an electric field by virtue of accumulating electric charges on two close surfaces insulated from each other. It is a passive electronic component with two terminals.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"The effect of a capacitor is known as capacitance. While some capacitance exists between any two electrical conductors in proximity in a circuit, a capacitor is a component designed to add capacitance to a circuit. The capacitor was originally known as the condenser,[1] a term still encountered in a few compound names, such as the condenser microphone."
    ),
    "cartridge_fuse" to Pair(
        "https://en.wikipedia.org/wiki/Fuse_(electrical)",
        "In electronics and electrical engineering, a fuse is an electrical safety device that operates to provide overcurrent protection of an electrical circuit. Its essential component is a metal wire or strip that melts when too much current flows through it, thereby stopping or interrupting the current. It is a sacrificial device; once a fuse has operated it is an open circuit, and must be replaced or rewired, depending on its type."
    ),
    "circuit_breaker" to Pair(
        "https://en.wikipedia.org/wiki/Circuit_breaker",
        "A circuit breaker is an electrical safety device designed to protect an electrical circuit from damage caused by overcurrent. Its basic function is to interrupt current flow to protect equipment and to prevent the risk of fire. Unlike a fuse, which operates once and then must be replaced, a circuit breaker can be reset (either manually or automatically) to resume normal operation."
    ),
    "filament_bulb" to Pair(
        "https://en.wikipedia.org/wiki/Incandescent_light_bulb",
        "An incandescent light bulb, incandescent lamp or incandescent light globe is an electric light with a wire filament heated until it glows. The filament is enclosed in a glass bulb with a vacuum or inert gas to protect the filament from oxidation. Current is supplied to the filament by terminals or wires embedded in the glass. A bulb socket provides mechanical support and electrical connections."
    ),
    "inductor" to Pair(
        "https://en.wikipedia.org/wiki/Inductor",
        "An inductor, also called a coil, choke, or reactor, is a passive two-terminal electrical component that stores energy in a magnetic field when electric current flows through it.[1] An inductor typically consists of an insulated wire wound into a coil.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"When the current flowing through the coil changes, the time-varying magnetic field induces an electromotive force (emf) (voltage) in the conductor, described by Faraday's law of induction. According to Lenz's law, the induced voltage has a polarity (direction) which opposes the change in current that created it. As a result, inductors oppose any changes in current through them."
    ),
    "led" to Pair(
        "https://en.wikipedia.org/wiki/Light-emitting_diode",
        "A light-emitting diode (LED) is a semiconductor device that emits light when current flows through it. Electrons in the semiconductor recombine with electron holes, releasing energy in the form of photons. The color of the light (corresponding to the energy of the photons) is determined by the energy required for electrons to cross the band gap of the semiconductor.[5] White light is obtained by using multiple semiconductors or a layer of light-emitting phosphor on the semiconductor device.[6]"
    ),
    "resistor" to Pair(
        "https://en.wikipedia.org/wiki/Resistor",
        "A resistor is a passive two-terminal electrical component that implements electrical resistance as a circuit element. In electronic circuits, resistors are used to reduce current flow, adjust signal levels, to divide voltages, bias active elements, and terminate transmission lines, among other uses. High-power resistors that can dissipate many watts of electrical power as heat may be used as part of motor controls, in power distribution systems, or as test loads for generators. Fixed resistors have resistances that only change slightly with temperature, time or operating voltage. Variable resistors can be used to adjust circuit elements (such as a volume control or a lamp dimmer), or as sensing devices for heat, light, humidity, force, or chemical activity."
    ),
    "transformer" to Pair(
        "https://en.wikipedia.org/wiki/Transformer",
        "A transformer is a passive component that transfers electrical energy from one electrical circuit to another circuit, or multiple circuits. A varying current in any coil of the transformer produces a varying magnetic flux in the transformer's core, which induces a varying electromotive force (EMF) across any other coils wound around the same core. Electrical energy can be transferred between separate coils without a metallic (conductive) connection between the two circuits. Faraday's law of induction, discovered in 1831, describes the induced voltage effect in any coil due to a changing magnetic flux encircled by the coil."
    ),
    "transistor" to Pair(
        "https://en.wikipedia.org/wiki/Transistor",
        "A transistor is a semiconductor device used to amplify or switch electrical signals and power. It is one of the basic building blocks of modern electronics.[1] It is composed of semiconductor material, usually with at least three terminals for connection to an electronic circuit. A voltage or current applied to one pair of the transistor's terminals controls the current through another pair of terminals. Because the controlled (output) power can be higher than the controlling (input) power, a transistor can amplify a signal. Some transistors are packaged individually, but many more in miniature form are found embedded in integrated circuits."
    ),
    "ic" to Pair(
        "https://en.wikipedia.org/wiki/Integrated_circuit",
        "An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, usually silicon. Large numbers of miniaturized transistors and other electronic components are integrated together on the chip. This results in circuits that are orders of magnitude smaller, faster, and less expensive than those constructed of discrete components, allowing a large transistor count. The IC's mass production capability, reliability, and building-block approach to integrated circuit design has ensured the rapid adoption of standardized ICs in place of designs using discrete transistors. ICs are now used in virtually all electronic equipment and have revolutionized the world of electronics. Computers, mobile phones and other home appliances are now inextricable parts of the structure of modern societies, made possible by the small size and low cost of ICs such as modern computer processors and microcontrollers."
    )
)

val linker4: HashMap<String, Pair<String, String>> = hashMapOf(
    "battery" to Pair(
        "https://en.wikipedia.org/wiki/Electric_battery",
        "A battery is a source of electric power consisting of one or more electrochemical cells with external connections[1] for powering electrical devices. When a battery is supplying power, its positive terminal is the cathode and its negative terminal is the anode.[2] The terminal marked negative is the source of electrons that will flow through an external electric circuit to the positive terminal. When a battery is connected to an external electric load, a redox reaction converts high-energy reactants to lower-energy products, and the free-energy difference is delivered to the external circuit as electrical energy. Historically the term \"battery\" specifically referred to a device composed of multiple cells; however, the usage has evolved to include devices composed of a single cell.[3]"
    ),
    "capacitor" to Pair(
        "https://en.wikipedia.org/wiki/Capacitor",
        "A capacitor is a device that stores electrical energy in an electric field by virtue of accumulating electric charges on two close surfaces insulated from each other. It is a passive electronic component with two terminals.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"The effect of a capacitor is known as capacitance. While some capacitance exists between any two electrical conductors in proximity in a circuit, a capacitor is a component designed to add capacitance to a circuit. The capacitor was originally known as the condenser,[1] a term still encountered in a few compound names, such as the condenser microphone."
    ),
    "cartridge_fuse" to Pair(
        "https://en.wikipedia.org/wiki/Fuse_(electrical)",
        "In electronics and electrical engineering, a fuse is an electrical safety device that operates to provide overcurrent protection of an electrical circuit. Its essential component is a metal wire or strip that melts when too much current flows through it, thereby stopping or interrupting the current. It is a sacrificial device; once a fuse has operated it is an open circuit, and must be replaced or rewired, depending on its type."
    ),
    "circuit_breaker" to Pair(
        "https://en.wikipedia.org/wiki/Circuit_breaker",
        "A circuit breaker is an electrical safety device designed to protect an electrical circuit from damage caused by overcurrent. Its basic function is to interrupt current flow to protect equipment and to prevent the risk of fire. Unlike a fuse, which operates once and then must be replaced, a circuit breaker can be reset (either manually or automatically) to resume normal operation."
    ),
    "filament_bulb" to Pair(
        "https://en.wikipedia.org/wiki/Incandescent_light_bulb",
        "An incandescent light bulb, incandescent lamp or incandescent light globe is an electric light with a wire filament heated until it glows. The filament is enclosed in a glass bulb with a vacuum or inert gas to protect the filament from oxidation. Current is supplied to the filament by terminals or wires embedded in the glass. A bulb socket provides mechanical support and electrical connections."
    ),
    "inductor" to Pair(
        "https://en.wikipedia.org/wiki/Inductor",
        "An inductor, also called a coil, choke, or reactor, is a passive two-terminal electrical component that stores energy in a magnetic field when electric current flows through it.[1] An inductor typically consists of an insulated wire wound into a coil.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"When the current flowing through the coil changes, the time-varying magnetic field induces an electromotive force (emf) (voltage) in the conductor, described by Faraday's law of induction. According to Lenz's law, the induced voltage has a polarity (direction) which opposes the change in current that created it. As a result, inductors oppose any changes in current through them."
    ),
    "led" to Pair(
        "https://en.wikipedia.org/wiki/Light-emitting_diode",
        "A light-emitting diode (LED) is a semiconductor device that emits light when current flows through it. Electrons in the semiconductor recombine with electron holes, releasing energy in the form of photons. The color of the light (corresponding to the energy of the photons) is determined by the energy required for electrons to cross the band gap of the semiconductor.[5] White light is obtained by using multiple semiconductors or a layer of light-emitting phosphor on the semiconductor device.[6]"
    ),
    "resistor" to Pair(
        "https://en.wikipedia.org/wiki/Resistor",
        "A resistor is a passive two-terminal electrical component that implements electrical resistance as a circuit element. In electronic circuits, resistors are used to reduce current flow, adjust signal levels, to divide voltages, bias active elements, and terminate transmission lines, among other uses. High-power resistors that can dissipate many watts of electrical power as heat may be used as part of motor controls, in power distribution systems, or as test loads for generators. Fixed resistors have resistances that only change slightly with temperature, time or operating voltage. Variable resistors can be used to adjust circuit elements (such as a volume control or a lamp dimmer), or as sensing devices for heat, light, humidity, force, or chemical activity."
    ),
    "transistor" to Pair(
        "https://en.wikipedia.org/wiki/Transistor",
        "A transistor is a semiconductor device used to amplify or switch electrical signals and power. It is one of the basic building blocks of modern electronics.[1] It is composed of semiconductor material, usually with at least three terminals for connection to an electronic circuit. A voltage or current applied to one pair of the transistor's terminals controls the current through another pair of terminals. Because the controlled (output) power can be higher than the controlling (input) power, a transistor can amplify a signal. Some transistors are packaged individually, but many more in miniature form are found embedded in integrated circuits."
    ),
    "integrated_micro-circuit" to Pair(
        "https://en.wikipedia.org/wiki/Integrated_circuit",
        "An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, usually silicon. Large numbers of miniaturized transistors and other electronic components are integrated together on the chip. This results in circuits that are orders of magnitude smaller, faster, and less expensive than those constructed of discrete components, allowing a large transistor count. The IC's mass production capability, reliability, and building-block approach to integrated circuit design has ensured the rapid adoption of standardized ICs in place of designs using discrete transistors. ICs are now used in virtually all electronic equipment and have revolutionized the world of electronics. Computers, mobile phones and other home appliances are now inextricable parts of the structure of modern societies, made possible by the small size and low cost of ICs such as modern computer processors and microcontrollers."
    ),
    "stabilizer" to Pair(
        "https://www.electricaltechnology.org/2016/11/what-is-voltage-stabilizer-how-it-works.html",
        "It is an electrical appliance which is designed to deliver a constant voltage to a load at its output terminals regardless of the changes in the input or incoming supply voltage. It protects the equipment or machine against over voltage, under voltage, and other voltage surges.\n" +
                "\n"
    ),
)

val linker6 : HashMap<String, Pair<String, String>> = hashMapOf(
    "battery" to Pair(
        "https://en.wikipedia.org/wiki/Electric_battery",
        "A battery is a source of electric power consisting of one or more electrochemical cells with external connections[1] for powering electrical devices. When a battery is supplying power, its positive terminal is the cathode and its negative terminal is the anode.[2] The terminal marked negative is the source of electrons that will flow through an external electric circuit to the positive terminal. When a battery is connected to an external electric load, a redox reaction converts high-energy reactants to lower-energy products, and the free-energy difference is delivered to the external circuit as electrical energy. Historically the term \"battery\" specifically referred to a device composed of multiple cells; however, the usage has evolved to include devices composed of a single cell.[3]"
    ),
    "capacitor" to Pair(
        "https://en.wikipedia.org/wiki/Capacitor",
        "A capacitor is a device that stores electrical energy in an electric field by virtue of accumulating electric charges on two close surfaces insulated from each other. It is a passive electronic component with two terminals.\\n\" +\n" +
                "            \"\\n\" +\n" +
                "            \"The effect of a capacitor is known as capacitance. While some capacitance exists between any two electrical conductors in proximity in a circuit, a capacitor is a component designed to add capacitance to a circuit. The capacitor was originally known as the condenser,[1] a term still encountered in a few compound names, such as the condenser microphone."
    ),
    "cartridge_fuse" to Pair(
        "https://en.wikipedia.org/wiki/Fuse_(electrical)",
        "In electronics and electrical engineering, a fuse is an electrical safety device that operates to provide overcurrent protection of an electrical circuit. Its essential component is a metal wire or strip that melts when too much current flows through it, thereby stopping or interrupting the current. It is a sacrificial device; once a fuse has operated it is an open circuit, and must be replaced or rewired, depending on its type."
    ),
    "circuit_breaker" to Pair(
        "https://en.wikipedia.org/wiki/Circuit_breaker",
        "A circuit breaker is an electrical safety device designed to protect an electrical circuit from damage caused by overcurrent. Its basic function is to interrupt current flow to protect equipment and to prevent the risk of fire. Unlike a fuse, which operates once and then must be replaced, a circuit breaker can be reset (either manually or automatically) to resume normal operation."
    ),
    "filament_bulb" to Pair(
        "https://en.wikipedia.org/wiki/Incandescent_light_bulb",
        "An incandescent light bulb, incandescent lamp or incandescent light globe is an electric light with a wire filament heated until it glows. The filament is enclosed in a glass bulb with a vacuum or inert gas to protect the filament from oxidation. Current is supplied to the filament by terminals or wires embedded in the glass. A bulb socket provides mechanical support and electrical connections."
    ),
    "integrated_circuit" to Pair(
        "https://en.wikipedia.org/wiki/Integrated_circuit",
        "An integrated circuit or monolithic integrated circuit (also referred to as an IC, a chip, or a microchip) is a set of electronic circuits on one small flat piece (or \"chip\") of semiconductor material, usually silicon. Large numbers of miniaturized transistors and other electronic components are integrated together on the chip. This results in circuits that are orders of magnitude smaller, faster, and less expensive than those constructed of discrete components, allowing a large transistor count. The IC's mass production capability, reliability, and building-block approach to integrated circuit design has ensured the rapid adoption of standardized ICs in place of designs using discrete transistors. ICs are now used in virtually all electronic equipment and have revolutionized the world of electronics. Computers, mobile phones and other home appliances are now inextricable parts of the structure of modern societies, made possible by the small size and low cost of ICs such as modern computer processors and microcontrollers."
    ),
    "led" to Pair(
        "https://en.wikipedia.org/wiki/Light-emitting_diode",
        "A light-emitting diode (LED) is a semiconductor device that emits light when current flows through it. Electrons in the semiconductor recombine with electron holes, releasing energy in the form of photons. The color of the light (corresponding to the energy of the photons) is determined by the energy required for electrons to cross the band gap of the semiconductor.[5] White light is obtained by using multiple semiconductors or a layer of light-emitting phosphor on the semiconductor device.[6]"
    ),
    "pulse_generator" to Pair("https://en.wikipedia.org/wiki/Pulse_generator", "A pulse generator is either an electronic circuit or a piece of electronic test equipment used to generate rectangular pulses. Pulse generators are used primarily for working with digital circuits; related function generators are used primarily for analog circuits."),
    "resistor" to Pair(
        "https://en.wikipedia.org/wiki/Resistor",
        "A resistor is a passive two-terminal electrical component that implements electrical resistance as a circuit element. In electronic circuits, resistors are used to reduce current flow, adjust signal levels, to divide voltages, bias active elements, and terminate transmission lines, among other uses. High-power resistors that can dissipate many watts of electrical power as heat may be used as part of motor controls, in power distribution systems, or as test loads for generators. Fixed resistors have resistances that only change slightly with temperature, time or operating voltage. Variable resistors can be used to adjust circuit elements (such as a volume control or a lamp dimmer), or as sensing devices for heat, light, humidity, force, or chemical activity."
    ),
    "transistor" to Pair(
        "https://en.wikipedia.org/wiki/Transistor",
        "A transistor is a semiconductor device used to amplify or switch electrical signals and power. It is one of the basic building blocks of modern electronics.[1] It is composed of semiconductor material, usually with at least three terminals for connection to an electronic circuit. A voltage or current applied to one pair of the transistor's terminals controls the current through another pair of terminals. Because the controlled (output) power can be higher than the controlling (input) power, a transistor can amplify a signal. Some transistors are packaged individually, but many more in miniature form are found embedded in integrated circuits."
    ),
)