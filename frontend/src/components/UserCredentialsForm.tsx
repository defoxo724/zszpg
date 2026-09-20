import { useState, type FormEvent } from 'react'
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form'
import type { RegistrationFieldErrors } from '../api/UserApi'

interface UserCredentialsFormProps {
	buttonLabel: string
	onSubmit: (login: string, password: string, passwordConfirmation: string) => Promise<void>
	fieldErrors?: RegistrationFieldErrors
	showPasswordConfirmation?: boolean
}

const UserCredentialsForm = ({
	buttonLabel,
	onSubmit,
	fieldErrors = {},
	showPasswordConfirmation = false,
}: UserCredentialsFormProps) => {
	const [login, setLogin] = useState('')
	const [password, setPassword] = useState('')
	const [passwordConfirmation, setPasswordConfirmation] = useState('')
	const [submitting, setSubmitting] = useState(false)

	const handleSubmit = async (event: FormEvent) => {
		event.preventDefault()
		setSubmitting(true)
		try {
			await onSubmit(login, password, passwordConfirmation)
		} finally {
			setSubmitting(false)
		}
	}

	return (
		<Form onSubmit={handleSubmit}>
			<Form.Group className="mb-3" controlId="login">
				<Form.Label>Login</Form.Label>
				<Form.Control
					id="login"
					value={login}
					onChange={(event) => setLogin(event.target.value)}
					isInvalid={Boolean(fieldErrors.login)}
					required
				/>
				<Form.Control.Feedback type="invalid">{fieldErrors.login}</Form.Control.Feedback>
			</Form.Group>
			<Form.Group className="mb-3" controlId="password">
				<Form.Label>Hasło</Form.Label>
				<Form.Control
					id="password"
					type="password"
					value={password}
					onChange={(event) => setPassword(event.target.value)}
					isInvalid={Boolean(fieldErrors.password)}
					required
				/>
				<Form.Control.Feedback type="invalid">{fieldErrors.password}</Form.Control.Feedback>
			</Form.Group>
			{showPasswordConfirmation && (
				<Form.Group className="mb-3" controlId="passwordConfirmation">
					<Form.Label>Powtórz hasło</Form.Label>
					<Form.Control
						id="passwordConfirmation"
						type="password"
						value={passwordConfirmation}
						onChange={(event) => setPasswordConfirmation(event.target.value)}
						isInvalid={Boolean(fieldErrors.passwordConfirmation)}
						required
					/>
					<Form.Control.Feedback type="invalid">
						{fieldErrors.passwordConfirmation}
					</Form.Control.Feedback>
				</Form.Group>
			)}
			<Button className="w-100" type="submit" disabled={submitting}>
				{submitting ? 'Proszę czekać...' : buttonLabel}
			</Button>
		</Form>
	)
}
export default UserCredentialsForm
