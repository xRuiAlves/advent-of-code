const reader = require("../utils/reader");

let min_num_parts_found1 = 0;
let bestQE = Infinity;
let max_size;
const main = (data) => {
    const presents = data.map(entry => parseInt(entry));
    max_size = presents.reduce((prev, curr) => prev + curr)/4;
    while (bestQE === Infinity && min_num_parts_found1 < presents.length - 2) {
        min_num_parts_found1++;
        getDivision([...presents], [], 0, 0, 1);
    }
    console.log(bestQE);
}

const getQE = (group) => {
    let val = 1;
    for (let entry of group) {
        val *= entry;
    }
    return val;
}

const getDivision = (pool, chosen, count, pool_index, depth) => {
    if (count > max_size || (depth === 1 && chosen.length > min_num_parts_found1)) {
        return;
    }
    if (count === max_size) {
        if (depth < 3) {
            let res = getDivision(pool, [], 0, 0, depth+1);
            if (res && depth === 1) {
                bestQE = Math.min(bestQE, getQE(chosen))
            }
        }
        return true;
    }
    for (let i = pool_index; i < pool.length; i++) {
        const [val] = pool.splice(i, 1);
        chosen.push(val);
        let res = getDivision(pool, chosen, count + val, i, depth);
        if (res && depth !== 1) {
            return true;
        }
        chosen.pop();
        pool.splice(i, 0, val);
    }
    return;
}

reader.readFile("input", main);

