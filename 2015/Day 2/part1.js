const reader = require("../utils/reader");

const main = (data) => {
    let feet = 0;
    data.forEach(box => {
        const dimensions = box.split("x").map(dim => parseInt(dim));
        const l = dimensions[0];
        const w = dimensions[1];
        const h = dimensions[2];
        const a1 = l*w;
        const a2 = l*h;
        const a3 = w*h;
        feet += 2*a1 + 2*a2 + 2*a3 + Math.min(a1,a2,a3);
    });
    console.log(feet);
}

reader.readFile("input", main);