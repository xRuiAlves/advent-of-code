const reader = require("../utils/reader");

const main = (data) => {
    const disks = data.map(elem => elem.split(" ")).map(raw_disk => ({
        size: parseInt(raw_disk[3]),
        position: parseInt(raw_disk[11])
    }));

    let time = 0;
    while(true) {
        let good = true;
        for (let i = 0; i < disks.length; i++) {
            if (((disks[i].position + time + i + 1) % disks[i].size) !== 0) {
                good = false;
                break;
            }
        }

        if (good) {
            console.log(time);
            process.exit();
        } else {
            time++;
        }
    }
}

reader.readFile("input", main);