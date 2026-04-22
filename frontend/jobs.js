const API_BASE = 'http://localhost:8080/api';
const token = localStorage.getItem('token');

document.addEventListener('DOMContentLoaded', () => {
    if (!token) {
        window.location.href = 'index.html';
    }

    loadJobs();

    document.getElementById('logoutBtn').addEventListener('click', () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        window.location.href = 'index.html';
    });
});

async function loadJobs() {
    try {
        const response = await fetch(`${API_BASE}/jobs`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        const data = await response.json();
        if (response.ok) {
            const jobList = document.getElementById('jobList');
            jobList.innerHTML = '';
            data.content.forEach(job => {
                const li = document.createElement('li');
                li.className = 'job-item';
                li.innerHTML = `
                    <h3>${job.title}</h3>
                    <p>${job.description}</p>
                    <p>Company: ${job.company}</p>
                    <p>Location: ${job.location}</p>
                    <p>Salary: ${job.salary}</p>
                    <button class="apply-btn" onclick="applyForJob(${job.id})">Apply</button>
                `;
                jobList.appendChild(li);
            });
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function applyForJob(jobId) {
    const resumeInput = document.createElement('input');
    resumeInput.type = 'file';
    resumeInput.accept = '.pdf,.doc,.docx';
    resumeInput.onchange = async (e) => {
        const file = e.target.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('jobId', jobId);
            formData.append('resume', file);

            try {
                const response = await fetch(`${API_BASE}/applications/apply`, {
                    method: 'POST',
                    headers: { 'Authorization': `Bearer ${token}` },
                    body: formData
                });
                if (response.ok) {
                    alert('Applied successfully');
                } else {
                    alert('Application failed');
                }
            } catch (error) {
                console.error('Error:', error);
            }
        }
    };
    resumeInput.click();
}