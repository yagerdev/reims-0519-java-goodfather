let typology = document.getElementById("typology");
let work = document.getElementById("work");
let material = document.getElementById("material");

typology.addEventListener("change", function() {
    work.disabled = false;
    for (const option of work) {
        option.style.display = "none";
    }
    work.value = "Sélectionnez...";
    let material = document.getElementById("material");
    material.disabled = false;
    for (const option of material) {
        option.style.display = "none";
    }
    material.value = "Sélectionnez...";
    if (typology.value != "Sélectionnez...") {
        for (const option of work) {
            if (option.getAttribute("filter") != null) {
                if (option.getAttribute("filter") == typology.value) {
                    option.style.display = "block";
                }
            }
            
        }
        let optionValues = [];
        for (const option of work) {
            if (option.getAttribute("filter") != null) {
                if (option.getAttribute("filter") == typology.value) {
                    if (optionValues.indexOf(option.value) > -1) {
                        option.style.display = "none";
                    } else {
                        optionValues.push(option.value);
                    }
                }
            }
        }
    }
});

work.addEventListener("change", function() {
    material.disabled = false;
    for (const option of material) {
        option.style.display = "none";
    }
    material.value = "Sélectionnez...";
    if (work.value != "Sélectionnez...") {
        for (const option of material) {
            if (option.getAttribute("filter") == (typology.value + " " + work.value)) {
                option.style.display = "block";
                option.selected = true;
            }
        }
    }
});