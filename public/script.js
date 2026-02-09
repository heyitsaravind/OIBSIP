// Global variables
let currentUser = null;
let currentPage = 1;
let currentSearch = '';
let currentCategory = '';

// API Base URL
const API_BASE = '/api';

// Initialize the application
document.addEventListener('DOMContentLoaded', function() {
    checkAuthStatus();
    loadCategories();
    loadBooks();
});

// Authentication functions
function checkAuthStatus() {
    const token = localStorage.getItem('token');
    if (token) {
        fetch(`${API_BASE}/auth/profile`, {
            headers: {
                'Authorization': `Bearer ${token}`
            }
        })
        .then(response => {
            if (response.ok) {
                return response.json();
            }
            throw new Error('Invalid token');
        })
        .then(user => {
            currentUser = user;
            updateNavigation();
            if (user.role === 'admin') {
                loadDashboard();
            }
        })
        .catch(() => {
            localStorage.removeItem('token');
            currentUser = null;
            updateNavigation();
        });
    }
}

function updateNavigation() {
    const authLink = document.getElementById('authLink');
    const userOnlyElements = document.querySelectorAll('.user-only');
    const adminOnlyElements = document.querySelectorAll('.admin-only');
    
    if (currentUser) {
        authLink.textContent = 'Logout';
        authLink.onclick = logout;
        
        userOnlyElements.forEach(el => el.style.display = 'block');
        
        if (currentUser.role === 'admin') {
            adminOnlyElements.forEach(el => el.style.display = 'block');
        }
    } else {
        authLink.textContent = 'Login';
        authLink.onclick = showAuthModal;
        
        userOnlyElements.forEach(el => el.style.display = 'none');
        adminOnlyElements.forEach(el => el.style.display = 'none');
    }
}

function login(event) {
    event.preventDefault();
    
    const email = document.getElementById('loginEmail').value;
    const password = document.getElementById('loginPassword').value;
    
    fetch(`${API_BASE}/auth/login`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            localStorage.setItem('token', data.token);
            currentUser = data.user;
            updateNavigation();
            closeModal('authModal');
            showSuccess('Login successful!');
            
            if (data.user.role === 'admin') {
                loadDashboard();
            }
        } else {
            showError(data.error || 'Login failed');
        }
    })
    .catch(error => {
        showError('Login failed: ' + error.message);
    });
}

function register(event) {
    event.preventDefault();
    
    const name = document.getElementById('registerName').value;
    const email = document.getElementById('registerEmail').value;
    const password = document.getElementById('registerPassword').value;
    const phone = document.getElementById('registerPhone').value;
    const address = document.getElementById('registerAddress').value;
    
    fetch(`${API_BASE}/auth/register`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ name, email, password, phone, address })
    })
    .then(response => response.json())
    .then(data => {
        if (data.token) {
            localStorage.setItem('token', data.token);
            currentUser = data.user;
            updateNavigation();
            closeModal('authModal');
            showSuccess('Registration successful!');
        } else {
            showError(data.error || 'Registration failed');
        }
    })
    .catch(error => {
        showError('Registration failed: ' + error.message);
    });
}

function logout() {
    localStorage.removeItem('token');
    currentUser = null;
    updateNavigation();
    showSection('home');
    showSuccess('Logged out successfully!');
}

// Navigation functions
function showSection(sectionId) {
    document.querySelectorAll('.section').forEach(section => {
        section.classList.remove('active');
    });
    document.getElementById(sectionId).classList.add('active');
    
    // Load section-specific data
    switch(sectionId) {
        case 'books':
            loadBooks();
            break;
        case 'my-books':
            if (currentUser) {
                loadMyBooks();
            }
            break;
        case 'admin':
            if (currentUser && currentUser.role === 'admin') {
                loadDashboard();
            }
            break;
    }
}

function toggleMenu() {
    const navMenu = document.getElementById('navMenu');
    navMenu.classList.toggle('active');
}

// Tab functions
function showTab(tabId) {
    const tabContents = document.querySelectorAll('.tab-content');
    const tabButtons = document.querySelectorAll('.tab-btn');
    
    tabContents.forEach(content => content.classList.remove('active'));
    tabButtons.forEach(btn => btn.classList.remove('active'));
    
    document.getElementById(tabId).classList.add('active');
    event.target.classList.add('active');
    
    // Load tab-specific data
    switch(tabId) {
        case 'current-books':
            loadCurrentBooks();
            break;
        case 'history':
            loadTransactionHistory();
            break;
        case 'reservations':
            loadReservations();
            break;
        case 'queries':
            loadQueries();
            break;
    }
}

function showAuthTab(tabId) {
    document.querySelectorAll('.auth-form').forEach(form => {
        form.classList.remove('active');
    });
    document.querySelectorAll('.auth-tabs .tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    document.getElementById(tabId).classList.add('active');
    event.target.classList.add('active');
}

function showAdminTab(tabId) {
    document.querySelectorAll('.admin-content').forEach(content => {
        content.classList.remove('active');
    });
    document.querySelectorAll('.admin-tabs .tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });
    
    document.getElementById(tabId).classList.add('active');
    event.target.classList.add('active');
    
    // Load admin tab-specific data
    switch(tabId) {
        case 'dashboard':
            loadDashboard();
            break;
        case 'manage-books':
            loadAdminBooks();
            break;
        case 'manage-members':
            loadAdminMembers();
            break;
        case 'transactions':
            loadAdminTransactions();
            break;
        case 'reports':
            loadReports();
            break;
    }
}

// Books functions
function loadCategories() {
    fetch(`${API_BASE}/books/meta/categories`)
    .then(response => response.json())
    .then(categories => {
        const categoryFilter = document.getElementById('categoryFilter');
        categoryFilter.innerHTML = '<option value="">All Categories</option>';
        
        categories.forEach(category => {
            const option = document.createElement('option');
            option.value = category;
            option.textContent = category;
            categoryFilter.appendChild(option);
        });
    })
    .catch(error => {
        console.error('Error loading categories:', error);
    });
}

function loadBooks(page = 1) {
    currentPage = page;
    const params = new URLSearchParams({
        page: page,
        limit: 12
    });
    
    if (currentSearch) params.append('search', currentSearch);
    if (currentCategory) params.append('category', currentCategory);
    
    fetch(`${API_BASE}/books?${params}`)
    .then(response => response.json())
    .then(data => {
        displayBooks(data.books);
        displayPagination(data.pagination);
    })
    .catch(error => {
        console.error('Error loading books:', error);
        showError('Failed to load books');
    });
}

function displayBooks(books) {
    const booksGrid = document.getElementById('booksGrid');
    
    if (books.length === 0) {
        booksGrid.innerHTML = '<div class="text-center">No books found</div>';
        return;
    }
    
    booksGrid.innerHTML = books.map(book => `
        <div class="book-card">
            <h3>${book.title}</h3>
            <div class="author">by ${book.author}</div>
            <div class="category">${book.category}</div>
            <div class="availability ${book.available_copies > 0 ? 'available' : 'unavailable'}">
                ${book.available_copies > 0 ? 
                    `${book.available_copies} available` : 
                    'Not available'
                }
            </div>
            ${book.description ? `<p class="text-muted">${book.description}</p>` : ''}
            <div class="book-actions">
                ${currentUser && book.available_copies === 0 ? 
                    `<button class="btn btn-secondary btn-small" onclick="reserveBook(${book.id})">Reserve</button>` : 
                    ''
                }
            </div>
        </div>
    `).join('');
}

function displayPagination(pagination) {
    const paginationDiv = document.getElementById('booksPagination');
    
    if (pagination.pages <= 1) {
        paginationDiv.innerHTML = '';
        return;
    }
    
    let paginationHTML = '';
    
    // Previous button
    if (pagination.page > 1) {
        paginationHTML += `<button onclick="loadBooks(${pagination.page - 1})">Previous</button>`;
    }
    
    // Page numbers
    for (let i = 1; i <= pagination.pages; i++) {
        if (i === pagination.page) {
            paginationHTML += `<button class="active">${i}</button>`;
        } else {
            paginationHTML += `<button onclick="loadBooks(${i})">${i}</button>`;
        }
    }
    
    // Next button
    if (pagination.page < pagination.pages) {
        paginationHTML += `<button onclick="loadBooks(${pagination.page + 1})">Next</button>`;
    }
    
    paginationDiv.innerHTML = paginationHTML;
}

function searchBooks() {
    currentSearch = document.getElementById('bookSearch').value;
    currentCategory = document.getElementById('categoryFilter').value;
    loadBooks(1);
}

function reserveBook(bookId) {
    if (!currentUser) {
        showAuthModal();
        return;
    }
    
    fetch(`${API_BASE}/transactions/reserve`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify({ bookId })
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            showSuccess(data.message);
        } else {
            showError(data.error || 'Failed to reserve book');
        }
    })
    .catch(error => {
        showError('Failed to reserve book: ' + error.message);
    });
}

// User books functions
function loadMyBooks() {
    loadCurrentBooks();
}

function loadCurrentBooks() {
    if (!currentUser) return;
    
    fetch(`${API_BASE}/transactions/my-books`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then(response => response.json())
    .then(books => {
        const grid = document.getElementById('currentBooksGrid');
        
        if (books.length === 0) {
            grid.innerHTML = '<div class="text-center">No books currently issued</div>';
            return;
        }
        
        grid.innerHTML = books.map(book => {
            const dueDate = new Date(book.due_date);
            const isOverdue = dueDate < new Date();
            
            return `
                <div class="book-card">
                    <h3>${book.title}</h3>
                    <div class="author">by ${book.author}</div>
                    <div class="category">${book.category}</div>
                    <div class="due-date ${isOverdue ? 'text-danger' : ''}">
                        Due: ${dueDate.toLocaleDateString()}
                        ${isOverdue ? ' (Overdue)' : ''}
                    </div>
                </div>
            `;
        }).join('');
    })
    .catch(error => {
        console.error('Error loading current books:', error);
    });
}

function loadTransactionHistory() {
    if (!currentUser) return;
    
    fetch(`${API_BASE}/transactions/history`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then(response => response.json())
    .then(transactions => {
        const grid = document.getElementById('historyGrid');
        
        if (transactions.length === 0) {
            grid.innerHTML = '<div class="text-center">No transaction history</div>';
            return;
        }
        
        grid.innerHTML = transactions.map(transaction => `
            <div class="transaction-item">
                <div class="transaction-header">
                    <h4>${transaction.title}</h4>
                    <span class="transaction-status status-${transaction.status}">${transaction.status}</span>
                </div>
                <div class="author">by ${transaction.author}</div>
                <div class="transaction-dates">
                    <div>Issued: ${new Date(transaction.issue_date).toLocaleDateString()}</div>
                    <div>Due: ${new Date(transaction.due_date).toLocaleDateString()}</div>
                    ${transaction.return_date ? 
                        `<div>Returned: ${new Date(transaction.return_date).toLocaleDateString()}</div>` : 
                        ''
                    }
                    ${transaction.fine_amount > 0 ? 
                        `<div class="fine">Fine: ₹${transaction.fine_amount}</div>` : 
                        ''
                    }
                </div>
            </div>
        `).join('');
    })
    .catch(error => {
        console.error('Error loading transaction history:', error);
    });
}

function loadReservations() {
    if (!currentUser) return;
    
    fetch(`${API_BASE}/transactions/reservations`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then(response => response.json())
    .then(reservations => {
        const grid = document.getElementById('reservationsGrid');
        
        if (reservations.length === 0) {
            grid.innerHTML = '<div class="text-center">No active reservations</div>';
            return;
        }
        
        grid.innerHTML = reservations.map(reservation => `
            <div class="book-card">
                <h3>${reservation.title}</h3>
                <div class="author">by ${reservation.author}</div>
                <div class="category">${reservation.category}</div>
                <div class="reservation-date">
                    Reserved: ${new Date(reservation.reservation_date).toLocaleDateString()}
                </div>
            </div>
        `).join('');
    })
    .catch(error => {
        console.error('Error loading reservations:', error);
    });
}

function loadQueries() {
    if (!currentUser) return;
    
    fetch(`${API_BASE}/reports/queries`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then(response => response.json())
    .then(queries => {
        const grid = document.getElementById('queriesGrid');
        
        if (queries.length === 0) {
            grid.innerHTML = '<div class="text-center">No queries submitted</div>';
            return;
        }
        
        grid.innerHTML = queries.map(query => `
            <div class="query-item">
                <div class="query-header">
                    <h4>${query.subject}</h4>
                    <span class="query-status status-${query.status}">${query.status}</span>
                </div>
                <div class="query-message">${query.message}</div>
                ${query.admin_response ? 
                    `<div class="admin-response">
                        <strong>Response:</strong> ${query.admin_response}
                    </div>` : 
                    ''
                }
                <div class="query-date">
                    Submitted: ${new Date(query.created_at).toLocaleDateString()}
                </div>
            </div>
        `).join('');
    })
    .catch(error => {
        console.error('Error loading queries:', error);
    });
}

// Admin functions
function loadDashboard() {
    if (!currentUser || currentUser.role !== 'admin') return;
    
    fetch(`${API_BASE}/reports/dashboard`, {
        headers: {
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        }
    })
    .then(response => response.json())
    .then(stats => {
        const statsGrid = document.getElementById('dashboardStats');
        
        statsGrid.innerHTML = `
            <div class="stat-card">
                <i class="fas fa-book" style="color: #007bff;"></i>
                <div class="number">${stats.totalBooks}</div>
                <div class="label">Total Books</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-book-open" style="color: #28a745;"></i>
                <div class="number">${stats.availableBooks}</div>
                <div class="label">Available Books</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-users" style="color: #17a2b8;"></i>
                <div class="number">${stats.totalMembers}</div>
                <div class="label">Total Members</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-exchange-alt" style="color: #ffc107;"></i>
                <div class="number">${stats.activeTransactions}</div>
                <div class="label">Active Transactions</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-exclamation-triangle" style="color: #dc3545;"></i>
                <div class="number">${stats.overdueBooks}</div>
                <div class="label">Overdue Books</div>
            </div>
            <div class="stat-card">
                <i class="fas fa-rupee-sign" style="color: #6f42c1;"></i>
                <div class="number">₹${stats.totalFines}</div>
                <div class="label">Total Fines</div>
            </div>
        `;
    })
    .catch(error => {
        console.error('Error loading dashboard:', error);
    });
}

// Modal functions
function showModal(modalId) {
    document.getElementById(modalId).style.display = 'block';
}

function closeModal(modalId) {
    document.getElementById(modalId).style.display = 'none';
}

function showAuthModal() {
    showModal('authModal');
}

function showBookModal(book = null) {
    const modal = document.getElementById('bookModal');
    const title = document.getElementById('bookModalTitle');
    
    if (book) {
        title.textContent = 'Edit Book';
        document.getElementById('bookId').value = book.id;
        document.getElementById('bookISBN').value = book.isbn || '';
        document.getElementById('bookTitle').value = book.title;
        document.getElementById('bookAuthor').value = book.author;
        document.getElementById('bookCategory').value = book.category;
        document.getElementById('bookPublisher').value = book.publisher || '';
        document.getElementById('bookYear').value = book.publication_year || '';
        document.getElementById('bookCopies').value = book.total_copies;
        document.getElementById('bookDescription').value = book.description || '';
    } else {
        title.textContent = 'Add New Book';
        document.getElementById('bookId').value = '';
        document.getElementById('bookISBN').value = '';
        document.getElementById('bookTitle').value = '';
        document.getElementById('bookAuthor').value = '';
        document.getElementById('bookCategory').value = '';
        document.getElementById('bookPublisher').value = '';
        document.getElementById('bookYear').value = '';
        document.getElementById('bookCopies').value = '1';
        document.getElementById('bookDescription').value = '';
    }
    
    showModal('bookModal');
}

function showQueryModal() {
    document.getElementById('querySubject').value = '';
    document.getElementById('queryMessage').value = '';
    showModal('queryModal');
}

function showIssueModal() {
    // Load members and available books
    loadMembersForIssue();
    loadAvailableBooksForIssue();
    showModal('issueModal');
}

function showReturnModal() {
    loadActiveTransactionsForReturn();
    showModal('returnModal');
}

// Form submission functions
function saveBook(event) {
    event.preventDefault();
    
    const bookId = document.getElementById('bookId').value;
    const bookData = {
        isbn: document.getElementById('bookISBN').value,
        title: document.getElementById('bookTitle').value,
        author: document.getElementById('bookAuthor').value,
        category: document.getElementById('bookCategory').value,
        publisher: document.getElementById('bookPublisher').value,
        publication_year: parseInt(document.getElementById('bookYear').value) || null,
        total_copies: parseInt(document.getElementById('bookCopies').value),
        description: document.getElementById('bookDescription').value
    };
    
    const url = bookId ? `${API_BASE}/books/${bookId}` : `${API_BASE}/books`;
    const method = bookId ? 'PUT' : 'POST';
    
    fetch(url, {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(bookData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            showSuccess(data.message);
            closeModal('bookModal');
            loadAdminBooks();
        } else {
            showError(data.error || 'Failed to save book');
        }
    })
    .catch(error => {
        showError('Failed to save book: ' + error.message);
    });
}

function submitQuery(event) {
    event.preventDefault();
    
    const queryData = {
        subject: document.getElementById('querySubject').value,
        message: document.getElementById('queryMessage').value
    };
    
    fetch(`${API_BASE}/reports/queries`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${localStorage.getItem('token')}`
        },
        body: JSON.stringify(queryData)
    })
    .then(response => response.json())
    .then(data => {
        if (data.message) {
            showSuccess(data.message);
            closeModal('queryModal');
            loadQueries();
        } else {
            showError(data.error || 'Failed to submit query');
        }
    })
    .catch(error => {
        showError('Failed to submit query: ' + error.message);
    });
}

// Utility functions
function showError(message) {
    // Create and show error message
    const errorDiv = document.createElement('div');
    errorDiv.className = 'error';
    errorDiv.textContent = message;
    
    document.body.appendChild(errorDiv);
    
    setTimeout(() => {
        document.body.removeChild(errorDiv);
    }, 5000);
}

function showSuccess(message) {
    // Create and show success message
    const successDiv = document.createElement('div');
    successDiv.className = 'success';
    successDiv.textContent = message;
    
    document.body.appendChild(successDiv);
    
    setTimeout(() => {
        document.body.removeChild(successDiv);
    }, 3000);
}

// Close modals when clicking outside
window.onclick = function(event) {
    const modals = document.querySelectorAll('.modal');
    modals.forEach(modal => {
        if (event.target === modal) {
            modal.style.display = 'none';
        }
    });
}

// Additional admin functions (to be implemented)
function loadAdminBooks() {
    // Implementation for loading books in admin view
    console.log('Loading admin books...');
}

function loadAdminMembers() {
    // Implementation for loading members in admin view
    console.log('Loading admin members...');
}

function loadAdminTransactions() {
    // Implementation for loading transactions in admin view
    console.log('Loading admin transactions...');
}

function loadReports() {
    // Implementation for loading reports
    console.log('Loading reports...');
}

function loadMembersForIssue() {
    // Implementation for loading members dropdown
    console.log('Loading members for issue...');
}

function loadAvailableBooksForIssue() {
    // Implementation for loading available books dropdown
    console.log('Loading available books for issue...');
}

function loadActiveTransactionsForReturn() {
    // Implementation for loading active transactions dropdown
    console.log('Loading active transactions for return...');
}

function issueBook(event) {
    event.preventDefault();
    // Implementation for issuing books
    console.log('Issuing book...');
}

function returnBook(event) {
    event.preventDefault();
    // Implementation for returning books
    console.log('Returning book...');
}