import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';

const Results = () => {
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadResults = async () => {
      try {
        const response = await axios.get('/api/exam/results');
        setResults(response.data.results);
      } catch (error) {
        console.error('Error loading results:', error);
      } finally {
        setLoading(false);
      }
    };

    loadResults();
  }, []);

  const formatTime = (seconds) => {
    const minutes = Math.floor(seconds / 60);
    const remainingSeconds = seconds % 60;
    return `${minutes}m ${remainingSeconds}s`;
  };

  const formatDate = (dateString) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getGrade = (percentage) => {
    if (percentage >= 90) return { grade: 'A+', color: '#28a745' };
    if (percentage >= 80) return { grade: 'A', color: '#28a745' };
    if (percentage >= 70) return { grade: 'B', color: '#ffc107' };
    if (percentage >= 60) return { grade: 'C', color: '#fd7e14' };
    if (percentage >= 50) return { grade: 'D', color: '#dc3545' };
    return { grade: 'F', color: '#dc3545' };
  };

  const calculateStats = () => {
    if (results.length === 0) return null;

    const totalExams = results.length;
    const averageScore = results.reduce((sum, result) => sum + result.percentage, 0) / totalExams;
    const bestScore = Math.max(...results.map(result => result.percentage));
    const totalTimeTaken = results.reduce((sum, result) => sum + result.timeTaken, 0);

    return {
      totalExams,
      averageScore: Math.round(averageScore),
      bestScore,
      totalTimeTaken
    };
  };

  if (loading) {
    return (
      <div className="container">
        <div className="loading">Loading your results...</div>
      </div>
    );
  }

  const stats = calculateStats();

  return (
    <div className="container">
      <div className="card">
        <h2>Exam Results</h2>
        
        {results.length === 0 ? (
          <div style={{ textAlign: 'center', padding: '40px' }}>
            <p style={{ fontSize: '18px', color: '#666', marginBottom: '20px' }}>
              You haven't taken any exams yet.
            </p>
            <Link to="/exam" className="btn btn-primary">
              Take Your First Exam
            </Link>
          </div>
        ) : (
          <>
            {/* Statistics Summary */}
            {stats && (
              <div className="score-details" style={{ marginBottom: '30px' }}>
                <div className="score-item">
                  <h3>Total Exams</h3>
                  <div style={{ fontSize: '24px', fontWeight: 'bold' }}>{stats.totalExams}</div>
                </div>
                <div className="score-item">
                  <h3>Average Score</h3>
                  <div style={{ fontSize: '24px', fontWeight: 'bold', color: '#007bff' }}>
                    {stats.averageScore}%
                  </div>
                </div>
                <div className="score-item">
                  <h3>Best Score</h3>
                  <div style={{ fontSize: '24px', fontWeight: 'bold', color: '#28a745' }}>
                    {stats.bestScore}%
                  </div>
                </div>
                <div className="score-item">
                  <h3>Total Time</h3>
                  <div style={{ fontSize: '24px', fontWeight: 'bold' }}>
                    {formatTime(stats.totalTimeTaken)}
                  </div>
                </div>
              </div>
            )}

            {/* Results List */}
            <div style={{ display: 'flex', flexDirection: 'column', gap: '15px' }}>
              {results.map((result, index) => {
                const gradeInfo = getGrade(result.percentage);
                
                return (
                  <div key={result.examId} className="card" style={{ margin: 0 }}>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', flexWrap: 'wrap', gap: '15px' }}>
                      <div>
                        <h4 style={{ margin: '0 0 5px 0' }}>
                          Exam #{results.length - index}
                        </h4>
                        <p style={{ margin: '0', color: '#666' }}>
                          {formatDate(result.submittedAt)}
                        </p>
                      </div>
                      
                      <div style={{ display: 'flex', alignItems: 'center', gap: '20px' }}>
                        <div style={{ textAlign: 'center' }}>
                          <div style={{ fontSize: '12px', color: '#666' }}>Score</div>
                          <div style={{ fontSize: '18px', fontWeight: 'bold' }}>
                            {result.score}/{result.totalQuestions}
                          </div>
                        </div>
                        
                        <div style={{ textAlign: 'center' }}>
                          <div style={{ fontSize: '12px', color: '#666' }}>Percentage</div>
                          <div style={{ fontSize: '18px', fontWeight: 'bold', color: gradeInfo.color }}>
                            {result.percentage}%
                          </div>
                        </div>
                        
                        <div style={{ textAlign: 'center' }}>
                          <div style={{ fontSize: '12px', color: '#666' }}>Grade</div>
                          <div style={{ 
                            fontSize: '24px', 
                            fontWeight: 'bold', 
                            color: gradeInfo.color,
                            padding: '5px 10px',
                            border: `2px solid ${gradeInfo.color}`,
                            borderRadius: '6px',
                            minWidth: '50px'
                          }}>
                            {gradeInfo.grade}
                          </div>
                        </div>
                        
                        <div style={{ textAlign: 'center' }}>
                          <div style={{ fontSize: '12px', color: '#666' }}>Time Taken</div>
                          <div style={{ fontSize: '16px', fontWeight: 'bold' }}>
                            {formatTime(result.timeTaken)}
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                );
              })}
            </div>

            <div style={{ textAlign: 'center', marginTop: '30px' }}>
              <Link to="/exam" className="btn btn-primary">
                Take Another Exam
              </Link>
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Results;