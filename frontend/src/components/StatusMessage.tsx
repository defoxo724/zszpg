interface StatusMessageProps {
	message: string | null
	variant?: 'danger' | 'success' | 'info'
}

const StatusMessage = ({ message, variant = 'danger' }: StatusMessageProps) => {
	if (!message) {
		return null
	}

	return (
		<div className={`alert alert-${variant}`} role="alert">
			{message}
		</div>
	)
}
export default StatusMessage
