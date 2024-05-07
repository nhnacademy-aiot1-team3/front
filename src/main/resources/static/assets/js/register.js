let idValid = 0;
let emailValid = 0;

function setIdValid(value,button_name) {
    idValid = value;
    checkBothConditions(button_name);
}

function setEmailValid(value,button_name) {
    emailValid = value;
    checkBothConditions(button_name);
}

function checkOnlyOne(element) {
    const checkboxes = document.getElementsByName("roles");
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
    const data = await res.json();
    console.log(data);
    return data;
}

const signUpButton = document.getElementById("sign-up-button");

function activateSignUpButton() {
    signUpButton.disabled = false;
}

document.getElementById("id").addEventListener('blur', async event=> {
    event.preventDefault();
    const id = document.getElementById("id");
    const id_languages = document.getElementById("id-languages");
    if(id.value.length === 0) {
        id_languages.innerText=("아이디를 입력하세요");
        return;
    }

    let url = "/id-check";
    let queryParam ="?id="+id.value;
    url += queryParam;

    const result = await check(url);

    if(result) {
        id_languages.innerText="이미 사용 중인 아이디입니다";
        setIdValid(0,"sign-up-button");
    } else {
        id_languages.innerText="사용 가능한 아이디입니다";
        setIdValid(1,"sign-up-button");
    }
})

document.getElementById("email-button").addEventListener('click', async event=> {
    event.preventDefault();
    btnDeactivate("email-button");
    const email = document.getElementById("email");
    const email_languages = document.getElementById("email-languages");

    let url = "/email/send";

    fetch(url, {
        method: "POST",
        headers: {
            'Accept':'application/json',
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            email: email.value
        }),
    })
        .then((response) => response.json())
        .then((result) => {
            console.log(result)
            const messageExist = result.hasOwnProperty('message');
            if(messageExist) {
                email_languages.innerText="인증 번호 발급 완료 3분 안에 인증을 완료하세요";

                if(!document.getElementById("email-code")){
                    const email_div = document.getElementById("email-check-div");
                    const email_code = document.createElement("input");
                    const email_code_button = document.createElement("button");
                    const margin_div = document.createElement("div");
                    const code_check_text = document.createElement("span");
                    const code_check = document.createElement("span");

                    email_code.id = "email-code";
                    email_code.className = "form-control";
                    margin_div.className = "mb-3";
                    email_code_button.id = "email-code-button";
                    email_code_button.className = "btn btn-primary w-100 py-8 mb-4 rounded-2";
                    email_code_button.innerText = "인증 코드 확인";

                    email_div.appendChild(email_code);
                    email_div.appendChild(margin_div);
                    email_div.appendChild(email_code_button);
                    email_div.appendChild(code_check_text);
                    email_div.appendChild(code_check);

                    document.getElementById("email-code-button").addEventListener('click', async event => {
                        event.preventDefault();
                        btnDeactivate("email-code-button");
                        const email = document.getElementById("email");
                        const certification_number = document.getElementById("email-code");

                        let url = "/email/verify";

                        fetch(url, {
                            method: "POST",
                            headers: {
                                'Accept':'application/json',
                                "Content-Type": "application/json"
                            },
                            body: JSON.stringify({
                                email: email.value,
                                certificationNumber: certification_number.value
                            }),
                        })
                            .then((response) => response.json())
                            .then((result) => {
                              console.log(result);

                              const messageExist = result.hasOwnProperty('message');
                                if(messageExist) {
                                    setEmailValid(1,"sign-up-button");
                                    code_check.innerText = "인증이 완료되었습니다.";
                                    email_languages.innerText="";
                                }

                                const result_message_exist = result.hasOwnProperty('resultMessage');
                                if(result_message_exist){
                                    code_check.innerText= result.resultMessage;
                                    btnActive("email-code-button");
                                }
                            })

                    })
                }
            }
            const result_message_exist = result.hasOwnProperty('resultMessage');
            if(result_message_exist){
                btnActive("email-button")
                email_languages.innerText= result.resultMessage;
            }
        })

})

function btnActive(button_name)  {
    const target = document.getElementById(button_name);
    target.disabled = false;
}
function btnDeactivate(button_name)  {
    const target = document.getElementById(button_name);
    target.disabled = true;
}
function checkBothConditions(button_name) {
    if (idValid === 1 && emailValid === 1) {
        btnActive(button_name);
    }else{
        btnDeactivate(button_name);
    }
}


function validateForm() {
    const checkboxes = document.querySelectorAll('input[name="roles"]:checked');
    if (checkboxes.length === 0) {
        alert("OWNER ViEWER 중 선택 해야합니다");
        return false;
    }
    return true;

}