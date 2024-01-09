const reader = require("../utils/reader");

const main = (data) => {
    let tls_count = 0;
    data.forEach(ip => {
        for (let i = 0; i < ip.length; i++) {
            if (ip[i] === "[") {
                const part = ip.substring(i+1, ip.indexOf("]", i));
                if (hasABBA(part)) {
                    return;
                }
            }
        }
        if (hasABBA(ip)) {
            tls_count++;
        }
    });
    console.log(tls_count);
}


const hasABBA = (str) => {
    for (let i = 0; i < str.length - 3; i++) {
        if (str[i] === str[i+3] && str[i+1] === str[i+2] && str[i] !== str[i+1]) {
            return true;
        }
    } 
    return false;
}

reader.readFile("input", main);