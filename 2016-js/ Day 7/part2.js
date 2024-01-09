const reader = require("../utils/reader");

const main = (data) => {
    let ssl_count = 0;
    data.forEach(ip => {
        for (let i = 0; i < ip.length - 2; i++) {
            if (ip[i] === "[" || ip[i+1] === "[") {
                i = ip.indexOf("]", i);
                continue;
            }
            if (ip[i] === ip[i+2] && ip[i] !== ip[i+1]) {
                const aba = ip.substr(i,3);
                if (findBAB(ip, aba)) {
                    ssl_count++;
                }
            }
        }
    });
    console.log(ssl_count);
}

const findBAB = (ip, aba) => {
    const bab = aba[1] + aba[0] + aba[1];
    let i = ip.indexOf("[");
    while (i !== -1) {
        const part = ip.substring(i+1, ip.indexOf("]", i));
        if (hasBAB(part, bab)) {
            return true;
        }
        i = ip.indexOf("[", i + 1);
    }
    return false;
}

const hasBAB = (part, bab) => {
    for (let i = 0; i < part.length - 2; i++) {
        if (part.substr(i,3) === bab) {
            return true;
        }
    } 
    return false;
}

reader.readFile("input", main);