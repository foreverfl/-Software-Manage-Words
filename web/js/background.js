chrome.runtime.onMessage.addListener(
    function (request, sender, sendResponse) {
        if (request.type === "setData") {
            console.log("setData입니다.");
            tmp = request.data;
        } else if (request.type === "getData") {
            console.log("getData입니다.");
            sendResponse(tmp);
        }
    }
);