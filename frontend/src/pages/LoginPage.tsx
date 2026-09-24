import { useState } from 'react'
import { Navigate, useLocation, useNavigate } from 'react-router-dom'
import AuthenticationCard from '../components/auth/AuthenticationCard'
import UserCredentialsForm from '../components/auth/UserCredentialsForm'
import StatusMessage from '../components/common/StatusMessage'
import { useAuth } from '../context/AuthContext'
import { showError, showSuccess } from '../utils/sweetAlert'

interface LoginLocationState {
	from?: string
}

const LoginPage = () => {
	const { user, signIn } = useAuth()
	const navigate = useNavigate()
	const location = useLocation()
	const [error, setError] = useState<string | null>(null)

	if (user) {
		return <Navigate to="/meals" replace />
	}

	const handleLogin = async (login: string, password: string) => {
		try {
			await signIn(login, password)
			void showSuccess('Zalogowano')

			const previousLocation = (location.state as LoginLocationState | null)?.from
			navigate(previousLocation ?? '/meals')
		} catch {
			const message = 'Nieprawidłowy login lub hasło.'
			setError(message)
			await showError('Błąd logowania', message)
		}
	}

	return (
		<AuthenticationCard
			title="Logowanie"
			footerText="Nie masz konta?"
			footerLinkText="Zarejestruj się"
			footerLinkTo="/register"
		>
			<StatusMessage message={error} />
			<UserCredentialsForm buttonLabel="Zaloguj" onSubmit={handleLogin} />
		</AuthenticationCard>
	)
}
export default LoginPage
