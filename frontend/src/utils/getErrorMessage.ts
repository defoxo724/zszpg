export const getErrorMessage = (error: unknown, fallbackMessage: string) => {
	if (error instanceof Error) {
		return error.message
	}

	return fallbackMessage
}
