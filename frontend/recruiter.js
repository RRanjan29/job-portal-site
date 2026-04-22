const API_BASE = 'http://localhost:8080/api';
const token = localStorage.getItem('token');

document.addEventListener('DOMContentLoaded', () => {
    if (!token || localStorage.getItem('role') !== 'RECRUITER') {
        window.location.href = 'index.html';
    }

    loadMyJobs();

    document.getElementById('postJobBtn').addEventListener('click', () => {
        const title = prompt('Job Title:');
        const description = prompt('Description:');
        const company = prompt('Company:');
        const location = prompt('Location:');
        const salary = prompt('Salary:');
        if (title && description && company && location) {
            postJob({ title, description, company, location, salary: parseFloat(salary) });
        }
    });

    document.getElementById('logoutBtn').addEventListener('click', () => {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        window.location.href = 'index.html';
    });
});

async function loadMyJobs() {
    try {
        const response = await fetch(`${API_BASE}/jobs/my`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        const jobs = await response.json();
        if (response.ok) {
            const jobList = document.getElementById('jobList');
            jobList.innerHTML = '';
            jobs.forEach(job => {
                const li = document.createElement('li');
                li.className = 'job-item';
                li.innerHTML = `
                    <h3>${job.title}</h3>
                    <p>${job.description}</p>
                    <p>Company: ${job.company}</p>
                    <p>Location: ${job.location}</p>
                    <p>Salary: ${job.salary}</p>
                    <button onclick="viewApplications(${job.id})">View Applications</button>
                    <button onclick="editJob(${job.id})">Edit</button>
                    <button onclick="deleteJob(${job.id})">Delete</button>
                `;
                jobList.appendChild(li);
            });
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function postJob(job) {
    try {
        const response = await fetch(`${API_BASE}/jobs`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(job)
        });
        if (response.ok) {
            loadMyJobs();
        } else {
            alert('Failed to post job');
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function viewApplications(jobId) {
    // For simplicity, alert the applications
    try {
        const response = await fetch(`${API_BASE}/applications/job/${jobId}`, {
            headers: { 'Authorization': `Bearer ${token}` }
        });
        const applications = await response.json();
        if (response.ok) {
            alert(JSON.stringify(applications, null, 2));
        }
    } catch (error) {
        console.error('Error:', error);
    }
}

async function editJob(jobId) {
    // Simple edit
    const title = prompt('New Title:');
    if (title) {
        // Similar to post
    }
}

async function deleteJob(jobId) {
    if (confirm('Delete job?')) {
        try {
            const response = await fetch(`${API_BASE}/jobs/${jobId}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${token}` }
            });
            if (response.ok) {
                loadMyJobs();
            }
        } catch (error) {
            console.error('Error:', error);
        }
    }
}