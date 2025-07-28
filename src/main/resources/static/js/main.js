document.querySelectorAll('.btn-confirm-js').forEach(button => {
    button.addEventListener('click', () => {
        const url = button.getAttribute('data-url');
        const modal = new bootstrap.Modal(document.getElementById('modal-confirm'));
        document.querySelector('#modal-confirm form').setAttribute('action', url);
        modal.show();
    });
});

function previewImage(event) {
    const file = event.target.files[0];
    const reader = new FileReader();

    reader.onload = function (e) {
        const preview = document.getElementById('preview');
        preview.src = e.target.result;
        preview.classList.remove('hidden');
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}