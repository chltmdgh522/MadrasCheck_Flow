let selectedCount;
const maxExtensions = 10;

$(document).ready(function () {

    selectedCount = parseInt($('#extensionCounter').data('count')) || 0;


    $('.fixed-checkbox').change(function () {
        const id = $(this).data('id');
        const selected = $(this).is(':checked');

        $.ajax({
            url: '/api/homework',
            type: 'PATCH',
            data: {
                id: id,
                selected: selected
            },
            success: function () {
                location.reload();
            },
            error: function () {
                alert('⚠ FIXED 업데이트 실패');
            }
        });
    });


    $('#addExtensionBtn').click(function () {
        let customExt = $('#customExtension').val().trim(); // 앞뒤 공백 제거

        customExt = customExt.replace(/\s+/g, ''); // 중간에 공백 제거 정규식

        if (!/^[a-zA-Z0-9]+$/.test(customExt)) {
            alert('확장자는 영문자와 숫자만 입력할 수 있습니다.');
            return;
        }

        if (!customExt) {
            alert('확장자를 입력하세요.');
            return;
        }

        if (selectedCount >= maxExtensions) {
            alert('최대 10개까지만 추가할 수 있습니다.');
            return;
        }

        $.ajax({
            url: '/api/homework',
            type: 'POST',
            data: { extension: customExt },
            success: function (res) {
                if (res.message === '실패') {
                    alert('이미 등록된 확장자입니다.');
                    return;
                }
                location.reload(); // 정상 등록이면 다시 그리기
            },
            error: function () {
                alert('⚠ 커스텀 확장자 추가 실패');
            }
        });
    });


    $('#customExtension').keypress(function (e) {
        if (e.which === 13) {
            $('#addExtensionBtn').click();
        }
    });


});
function removeExtension(id) {
    $.ajax({
        url: '/api/homework',
        type: 'DELETE',
        data: { id: id },
        success: function () {
            location.reload();
        },
        error: function () {
            alert('⚠ 삭제 실패');
        }
    });
}


function getRegisteredExtensions() {
    const fixed = $(".fixed-checkbox:checked").map(function () {
        return $(this).val();
    }).get();

    const custom = $("#extensionsDisplay .extension-tag").map(function () {
        return $(this).children('span').first().text().trim();
    }).get();

    return fixed.concat(custom);
}

function autoCheckExtension(file) {
    const resultBox = $('#checkResult');
    resultBox.empty();

    if (!file) {
        resultBox.text("⚠ 파일을 선택해주세요.");
        return;
    }

    const fileName = file.name;
    const ext = fileName.split('.').pop();
    const registered = getRegisteredExtensions();

    const isMatched = registered.includes(ext);

    resultBox.html(`
        <div><strong>확장자: <code>.${ext}</code></strong></div>
        ${isMatched
        ? '<div class="invalid-ext">✘ 등록된 확장자입니다. 파일 전송은 어렵습니다.</div>'
        : '<div class="valid-ext">✔ 등록되지 않은 확장자입니다. 파일 전송 가능합니다.</div>'}
    `);
}

$('#fileInput').on('change', function () {
    const file = this.files[0];
    autoCheckExtension(file);
});
