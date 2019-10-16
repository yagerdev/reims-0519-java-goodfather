let typology = document.getElementById("typology");
let work = document.getElementById("work");
let material = document.getElementById("material");

let works = [].slice.call(work);
let materials = [].slice.call(material);

typology.addEventListener("change", function() {
    work.disabled = false;
    work.selectedIndex = 0;
    material.disabled = true;
    material.selectedIndex = 0;
    for (i = 1; i < works.length; i ++) {
        work.options[1] = null;
    }
    for (i = 1; i < materials.length; i++) {
        material.options[1] = null;
    }
    let optionValues = [];
    if (typology.value != "Sélectionnez...") {
        for(const option of works) {
            if (option.getAttribute("filter") != null) {
                if (option.getAttribute("filter") == typology.value) {
                    if (optionValues.indexOf(option.value) == -1) {
                        work.add(option);
                    }
                    optionValues.push(option.value);
                } 
            }
        }
        if (work.options.length == 1) {
            work.disabled = true;
        }
    }
});

work.addEventListener("change", function() {
    material.disabled = false;
    material.selectedIndex = 0;
    for (i = 1; i < materials.length; i++) {
        material.options[1] = null;
    }
    let optionValues = [];
    if (work.value != "Sélectionnez...") {
        for (const option of materials) {
            if(option.getAttribute("filter") != null) {
                if (option.getAttribute("filter") == (typology.value + " " + work.value)) {
                    if (optionValues.indexOf(option.value) == -1) {
                        material.add(option);
                    }
                    optionValues.push(option.value);
                }
            }
            
        }
        if (material.options.length == 1) {
            material.disabled = true;
        }
    }
});