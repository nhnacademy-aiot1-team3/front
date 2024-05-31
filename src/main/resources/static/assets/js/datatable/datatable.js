jQuery(function($){
    $(".datatable").DataTable(
        {
            language: {
                "lengthMenu": '<select class="form-control">'+
                    '<option value="5">5</option>'+
                    '<option value="10">10</option>'+
                    '<option value="25">25</option>'+
                    '<option value="-1">ALL</option>'+
                    '</select>'
            }
        }
    );
});