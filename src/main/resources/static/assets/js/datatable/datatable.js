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
            },
            autoWidth: false, /* 자동 너비 조정을 비활성화 */
            columnDefs: [
                { width: '100%'} /* 각 컬럼의 너비를 설정 */
            ]
        }
    );
});