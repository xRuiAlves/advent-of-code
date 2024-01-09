const reader = require("../utils/reader");

const main = (data) => {
    let feet = 0;
    data.forEach(box => {
        const dimensions = box.split("x").map(dim => parseInt(dim)).sort((a1, a2) => a1 > a2);
        const l = dimensions[0];
        const w = dimensions[1];
        const h = dimensions[2];
        feet += 2*l + 2*w + l*w*h;
    });
    console.log(feet);
}

reader.readFile("input", main);