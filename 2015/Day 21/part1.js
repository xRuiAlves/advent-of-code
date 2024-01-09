const reader = require("../utils/reader");

const weapons_list = [
    {
        cost: 8,
        damage: 4,
        armor: 0
    },
    {
        cost: 10,
        damage: 5,
        armor: 0
    },
    {
        cost: 25,
        damage: 6,
        armor: 0
    },
    {
        cost: 40,
        damage: 7,
        armor: 0
    },
    {
        cost: 74,
        damage: 8,
        armor: 0
    }
];
const armors_list = [
    {
        cost: 13,
        damage: 0,
        armor: 1
    },
    {
        cost: 31,
        damage: 0,
        armor: 2
    },
    {
        cost: 53,
        damage: 0,
        armor: 3
    },
    {
        cost: 75,
        damage: 0,
        armor: 4
    },
    {
        cost: 102,
        damage: 0,
        armor: 5
    }
];
const rings_list = [
    {
        cost: 25,
        damage: 1,
        armor: 0
    },
    {
        cost: 50,
        damage: 2,
        armor: 0
    },
    {
        cost: 100,
        damage: 3,
        armor: 0
    },
    {
        cost: 20,
        damage: 0,
        armor: 1
    },
    {
        cost: 40,
        damage: 0,
        armor: 2
    },
    {
        cost: 80,
        damage: 0,
        armor: 3
    }
];

const main = (data) => {
    data = data.map(entry => entry.split(" "));
    const player_hp = 100;
    const boss = {
        hp: parseInt(data[0][2]),
        damage: parseInt(data[1][1]),
        armor: parseInt(data[2][1])
    }
    
    const weapon_options = buildWeaponOptions();
    const armor_options = buildArmorOptions();
    const rings_options = buildRingOptions();

    const builds = getPossibleBuilds(weapon_options, armor_options, rings_options);
    builds.sort((b1, b2) => {
        if (b1.cost < b2.cost){
          return -1;
        }
        if (b1.cost > b2.cost){
          return 1;
        }
        return 0;
    });

    for (let build of builds) {
        if (fight(build, boss) === "player") {
            console.log(build.cost);
            return;
        }
    }
}

const buildWeaponOptions = () => weapons_list.map((_, i) => 
    (Math.pow(2, weapons_list.length) + Math.pow(2, weapons_list.length - i - 1)).toString(2).substr(1, weapons_list.length).split("").map(num => parseInt(num)));

const buildArmorOptions = () => {
    const options = armors_list.map((_, i) => 
        (Math.pow(2, armors_list.length) + Math.pow(2, armors_list.length - i - 1)).toString(2).substr(1, armors_list.length).split("").map(num => parseInt(num)));
    options.splice(0, 0, new Array(armors_list.length).fill(0));
    return options;
}

const buildRingOptions = () => {
    const options = rings_list.map((_, i) => 
        (Math.pow(2, rings_list.length) + Math.pow(2, rings_list.length - i - 1)).toString(2).substr(1, rings_list.length).split("").map(num => parseInt(num)));
    options.splice(0, 0, new Array(rings_list.length).fill(0));
    for (let i = 0; i < rings_list.length - 1; i++) {
        for (let j = i+1; j < rings_list.length; j++) {
            const ring_option = [...options[0]];
            ring_option[i] = 1;
            ring_option[j] = 1;
            options.push(ring_option);
        }
    }
    return options;
}

const getPossibleBuilds = (weapons_options, armor_options, rings_options) => {
    const builds = [];
    weapons_options.forEach(weapon_option => {
        armor_options.forEach(armor_option => {
            rings_options.forEach(ring_option => {
                const build = {
                    cost: 0,
                    damage: 0,
                    armor: 0
                }
                for (let i = 0; i < weapon_option.length; i++) {
                    build.cost += weapons_list[i].cost * weapon_option[i];
                    build.damage += weapons_list[i].damage * weapon_option[i];
                    build.armor += weapons_list[i].armor * weapon_option[i];
                }
                for (let i = 0; i < armor_option.length; i++) {
                    build.cost += armors_list[i].cost * armor_option[i];
                    build.damage += armors_list[i].damage * armor_option[i];
                    build.armor += armors_list[i].armor * armor_option[i];
                }
                for (let i = 0; i < ring_option.length; i++) {
                    build.cost += rings_list[i].cost * ring_option[i];
                    build.damage += rings_list[i].damage * ring_option[i];
                    build.armor += rings_list[i].armor * ring_option[i];
                }
                builds.push(build);
            });
        });
    });
    return builds;
}

const fight = (player, boss) => {
    const opponent = {...boss};
    player.hp = 100;
    while (true) {
        opponent.hp -= Math.max(1, player.damage - opponent.armor);
        if (opponent.hp <= 0) {
            return "player";
        }
        player.hp -= Math.max(1, opponent.damage - player.armor);
        if (player.hp <= 0) {
            return "boss";
        }
    }
}

reader.readFile("input", main);

