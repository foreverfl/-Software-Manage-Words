// button event
document.querySelector('#addAll').addEventListener('click', addAll);

// adding all
function addAll() {

    // inputting data to controller
    chrome.runtime.sendMessage({ type: "getData" }, function (data) {

        const dataParsed = JSON.parse(data);
        console.log(dataParsed);

        copyJsonToClipboard(dataParsed);

    })
}

function exportToServer(jsonValue) {
    fetch('http://localhost:8000', {
        method: 'POST',
        body: JSON.stringify(jsonValue)
    })
        .then(response => {
            console.log('Data sent successfully');
        })
        .catch(error => {
            console.error('Error:', error);
        });
}

function copyJsonToClipboard(jsonValue) {
    // Json to String
    const jsonString = JSON.stringify(jsonValue);
    const textArea = document.createElement('textarea');
    textArea.value = jsonString;
    document.body.appendChild(textArea);

    // selection & copy
    textArea.select();
    document.execCommand('copy');

    // removing text Area
    document.body.removeChild(textArea);
}

function exportJson(jasonValue) {
    // JSON 데이터를 문자열로 변환
    const json = JSON.stringify(jasonValue);

    // Blob 객체 생성
    const blob = new Blob([json], { type: "application/json" });

    // Blob URL 생성
    const url = URL.createObjectURL(blob);

    // a 태그 생성
    const link = document.createElement("a");
    link.href = url;
    link.download = "data.json";

    // 클릭 이벤트 발생시켜 파일 내보내기
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);

    // Blob URL 해제
    URL.revokeObjectURL(url);
}

