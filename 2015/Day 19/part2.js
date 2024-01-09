const reader = require("../utils/reader");

const decompositions = {};
const main = (data) => {
    const target_molecule = data[data.length-1];
    const decomposed_molecule = decomposeMolecule(target_molecule);
    const [rn, ar, y] = countRhsOnlyMolecules(decomposed_molecule);
    const num_steps = decomposed_molecule.length - rn - ar - 2*y - 1;
    console.log(num_steps);
}

const decomposeMolecule = (molecule) => {
    const atoms = [];
    for (let i = 0; i < molecule.length; i++) {
        if (i < molecule.length-1 && isLowerCase(molecule[i+1])) {
            atoms.push(molecule.substr(i, 2));
            i++;
        } else {
            atoms.push(molecule[i]);
        }
    }
    return atoms;
}

const isLowerCase = (char) => char.toLowerCase() === char;

countRhsOnlyMolecules = (decomposed_molecule) => {
    let [rn, ar, y] = [0, 0, 0]
    decomposed_molecule.forEach(atom => {
        if (atom === "Rn") {
            rn++;
        } else if (atom === "Ar") {
            ar++;
        } else if (atom === "Y") {
            y++;
        }
    });
    return [rn, ar, y];
}

reader.readFile("input", main);

