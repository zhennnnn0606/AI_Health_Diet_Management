const API_BASE_URL = '/api/meals';
const analyzeBtn = document.getElementById('analyzeBtn');
const mealInput = document.getElementById('mealInput');
const loadingDiv = document.getElementById('loading');
const resultsSection = document.getElementById('resultsSection');
const historyContainer = document.getElementById('historyContainer');

analyzeBtn.addEventListener('click', analyzeMeal);
mealInput.addEventListener('keydown', (e) => {
    if (e.ctrlKey && e.key === 'Enter') {
        analyzeMeal();
    }
});

async function analyzeMeal() {
    const mealDescription = mealInput.value.trim();
    
    if (!mealDescription) {
        alert('請輸入餐食描述');
        return;
    }

    analyzeBtn.disabled = true;
    loadingDiv.style.display = 'flex';
    resultsSection.style.display = 'none';

    try {
        const response = await fetch(`${API_BASE_URL}/analyze`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ mealDescription })
        });

        if (!response.ok) {
            throw new Error('分析失敗，請稍後重試');
        }

        const data = await response.json();
        displayResults(data);
        mealInput.value = '';
        await loadHistory();
    } catch (error) {
        console.error('Error:', error);
        alert('錯誤: ' + error.message);
    } finally {
        analyzeBtn.disabled = false;
        loadingDiv.style.display = 'none';
    }
}

function displayResults(data) {
    document.getElementById('resultMealName').textContent = data.mealName || '未知餐食';
    document.getElementById('resultNutritionAnalysis').textContent = data.nutritionAnalysis || '暫無數據';
    document.getElementById('resultRecommendations').textContent = data.recommendations || '暫無建議';
    
    const scoreValue = Math.min(Math.max(data.healthScore || 0, 0), 10);
    document.getElementById('scoreValue').textContent = `${scoreValue}/10`;
    document.getElementById('scoreBar').style.width = `${(scoreValue / 10) * 100}%`;

    const ingredientsList = document.getElementById('resultIngredients');
    ingredientsList.innerHTML = '';
    if (data.ingredients && Array.isArray(data.ingredients)) {
        data.ingredients.forEach(ingredient => {
            const tag = document.createElement('span');
            tag.className = 'ingredient-tag';
            tag.textContent = ingredient;
            ingredientsList.appendChild(tag);
        });
    }

    resultsSection.style.display = 'block';
}

async function loadHistory() {
    try {
        const response = await fetch(`${API_BASE_URL}/history`);
        const meals = await response.json();

        historyContainer.innerHTML = '';
        
        if (!meals || meals.length === 0) {
            historyContainer.innerHTML = '<p class="empty-message">暫無歷史記錄</p>';
            return;
        }

        meals.forEach(meal => {
            const item = document.createElement('div');
            item.className = 'history-item';
            
            const ingredients = meal.ingredients ? JSON.parse(meal.ingredients) : [];
            const ingredientsStr = Array.isArray(ingredients) ? ingredients.slice(0, 3).join(', ') : '';
            
            item.innerHTML = `
                <div class="history-item-header">
                    <div class="history-item-title">${escapeHtml(meal.mealName || '未知')}</div>
                    <div class="history-item-score">${meal.healthScore || 0}/10</div>
                </div>
                <div class="history-item-time">${formatDate(meal.createdAt)}</div>
                <div class="history-item-description">${escapeHtml(meal.originalDescription || '')}</div>
                <button class="btn-delete" onclick="deleteMeal(${meal.id})">刪除</button>
            `;
            
            historyContainer.appendChild(item);
        });
    } catch (error) {
        console.error('Failed to load history:', error);
    }
}

async function deleteMeal(id) {
    if (confirm('確定要刪除這筆記錄嗎？')) {
        try {
            const response = await fetch(`${API_BASE_URL}/${id}`, {
                method: 'DELETE'
            });

            if (response.ok) {
                await loadHistory();
            } else {
                alert('刪除失敗');
            }
        } catch (error) {
            console.error('Error:', error);
            alert('錯誤: ' + error.message);
        }
    }
}

function formatDate(dateString) {
    if (!dateString) return '';
    const date = new Date(dateString);
    const now = new Date();
    const diffTime = Math.abs(now - date);
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    
    if (diffDays === 0) {
        return '今天 ' + date.toLocaleTimeString('zh-TW', { hour: '2-digit', minute: '2-digit' });
    } else if (diffDays === 1) {
        return '昨天';
    } else if (diffDays < 7) {
        return diffDays + '天前';
    } else {
        return date.toLocaleDateString('zh-TW');
    }
}

function escapeHtml(text) {
    const div = document.createElement('div');
    div.textContent = text;
    return div.innerHTML;
}

// Load history on page load
window.addEventListener('DOMContentLoaded', loadHistory);
