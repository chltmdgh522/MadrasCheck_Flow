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
            success: function () {},
            error: function () {
                alert('⚠ FIXED 업데이트 실패');
            }
        });
    });


    $('#addExtensionBtn').click(function () {
        const customExt = $('#customExtension').val().trim();

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