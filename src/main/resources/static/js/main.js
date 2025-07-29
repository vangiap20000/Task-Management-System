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
        const removeImageBtn = document.getElementById('removeImageBtn');
        preview.src = e.target.result;
        preview.classList.remove('hidden');
        removeImageBtn.classList.remove('hidden');
    };

    if (file) {
        reader.readAsDataURL(file);
    }
}

function removeImage() {
    const isDeleteFile = 1;
    const preview = document.getElementById('preview');
    const removeImageBtn = document.getElementById('removeImageBtn');
    const photo = document.getElementById('photo');

    preview.classList.add('hidden');
    removeImageBtn.classList.add('hidden');
    photo.value = '';
    document.getElementById('deleteFile').value = isDeleteFile;
}