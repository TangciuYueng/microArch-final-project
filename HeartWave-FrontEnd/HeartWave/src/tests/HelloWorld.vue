<template>
    <div>
        <div>
            <h1>
                {{ msg }}
            </h1>
            <p>...</p>
        </div>
    </div>
</template>

<script setup>
defineProps({
    msg: {
        type: String,
        required: true,
    },
});
</script>