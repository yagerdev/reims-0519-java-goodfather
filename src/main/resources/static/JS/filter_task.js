$("#typology").on("change", function() {
    let typology = $(this).val(); 
    $("#work").find("option").hide();
    $("#work").val('Sélectionnez...');
    $("#material").find("option").hide();
    $("#material").val('Sélectionnez...');
    if (typology) {
        $("#work").find("option[filter='" + typology + "']")
                .show();

        let optionValues =[];
        $("#work option").each(function(){
            if (this.getAttribute('filter') == $("#typology").val()) {
                if($.inArray(this.value, optionValues) >-1){
                    $(this).hide();
                }
                else {
                    optionValues.push(this.value);
                }
            }
        });
    }
});

$("#work").on("change", function() {
    let typology = $("#typology").val();
    let work = $(this).val().toLowerCase();
    $("#material").find("option").hide();
    $("#material").val('Sélectionnez...');
    if (work) {
        $("#material").find("option[filter='" + typology + " " + work + "']")
                    .show()
                    .prop('selected', true);
    }
});