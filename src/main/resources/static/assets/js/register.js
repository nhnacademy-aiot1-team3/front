function checkOnlyOne(element) {
    const checkboxes = document.getElementsByName("role");
    checkboxes.forEach((cb) => {
        cb.checked = false;
    });
    element.checked = true;
    const labelId = checkbox.getAttribute('id');
    const associatedLabel = document.querySelector('label[for="' + labelId + '"]');
    associatedLabel.click();
}

async function check(url){
    const res = await fetch(url);
    console.log(res);
    console.log("res after, data before")
    const data = await res.json();
    console.log(data);
    return data;
}

document.getElementById("id").addEventListener('blur', async event=> {
    event.preventDefault();
    const id = document.getElementById("id");
    const id_languages = document.getElementById("id-languages");
    if(id.value.length === 0) {
        alert("아이디를 입력하세요");
        return;
    }

    let url = "/pre_login/idCheck";
    let queryParam ="?id="+id.value;
    url += queryParam;
    console.log("url:" + url);

    const result = await check(url);

    console.log("result: idcheck 밑")
    console.log(result);
    if(result) {
        id_languages.innerText="이미 사용 중인 아이디입니다";
    } else {
        id_languages.innerText="사용 가능한 아이디입니다";
    }
})
// 승인할때 /pre-login/email-check

document.getElementById("email-button").addEventListener('click', async event=> {
    event.preventDefault();
    const email = document.getElementById("email");

    let url = "/pre-login/email-code";
    let queryParam ="?email="+email.value;
    url += queryParam;

    const result = await check(url);

    const email_div = document.getElementById("email-check-div");
    const email_code = document.createElement("input");
    const email_code_button = document.createElement("button");
    const margin_div = document.createElement("div");
    const code_check_text = document.createElement("span");
    
    email_code.id="email-code";
    email_code.className="form-control";

    margin_div.className="mb-3";

    email_code_button.id="email-code-button";
    email_code_button.className="btn btn-primary w-100 py-8 mb-4 rounded-2";
    email_code_button.innerText="코드 확인";

    email_div.appendChild(email_code);
    email_div.appendChild(margin_div);
    email_div.appendChild(email_code_button);
    email_div.appendChild(code_check_text);

    // TODO 3분 타이머 띄우기 시도

    // TODO 입력 맞으면 인증 완료 문구 띄우기 아니면 잘못된 입력이라고 띄워라
    document.getElementById("email-code-button").addEventListener('click', async event => {
        event.preventDefault();

        if(result){
            console.log("인증 완");
            code_check_text.innerText="인증 완료";
        } else {
            console.log("인증 실패");
            code_check_text.innerText="잘못된 코드를 입력하였습니다";
        }
    })
})


function validateForm() {
    const checkboxes = document.querySelectorAll('input[name="role"]:checked');
    if (checkboxes.length === 0) {
        alert("OWNER ViEWER 중 선택 해야합니다");
        return false;
    }
    return true;

}