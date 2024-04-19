function checkOnlyOne(element) {
    const checkboxes = document.getElementsByName("role");
    checkboxes.forEach((cb) => {
        cb.checked = false;
    });
    element.checked = true;
    var labelId = checkbox.getAttribute('id');
    var associatedLabel = document.querySelector('label[for="' + labelId + '"]');
    associatedLabel.click();
}
async function idCheck(){
    let url = "http://localhost:8080/idCheck";
    let queryParam ="?id="+id.value;
    url += queryParam;
    const res = await fetch(url);
    const data = await res.json();
    console.log(res);
    console.log("res after, data before")
    console.log(data);
    return data;
}


document.getElementById("id_check").addEventListener('click', async event=> {
    event.preventDefault();
    const id = document.getElementById("id");
    const id_languages = document.getElementById("id_languages");
    if(id.value.length === 0) {
        alert("아이디를 입력하세요");
        return;
    }

    // const a = idCheck();

    const result = await idCheck();

    console.log("result: idcheck 밑")
    console.log(result);
    if(result) {
        id_languages.innerText="";
        id_languages.innerText="사용 가능한 아이디입니다";
    } else {
        id_languages.innerText="이미 사용 중인 아이디입니다";
    }
})

function validateForm() {
    var checkboxes = document.querySelectorAll('input[name="role"]:checked');
    if (checkboxes.length === 0) {
        alert("OWNER ViEWER 중 선택 해야합니다");
        return false;
    }
    return true;

}